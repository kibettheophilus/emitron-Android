package com.raywenderlich.emitron.ui.download.workers

import android.content.Context
import androidx.work.*
import com.raywenderlich.emitron.data.login.LoginRepository
import com.raywenderlich.emitron.di.modules.worker.ChildWorkerFactory
import com.raywenderlich.emitron.model.PermissionTag
import com.raywenderlich.emitron.model.isDownloadPermissionTag
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 *  Worker for verifying a downloads every 7th day,
 *
 * It will fetch the permissions, if it fails due to any issue,
 * existing download permissions will be removed.
 */
class VerifyDownloadWorker @AssistedInject constructor(
  @Assisted private val appContext: Context,
  @Assisted private val workerParameters: WorkerParameters,
  private val loginRepository: LoginRepository
) : CoroutineWorker(appContext, workerParameters) {

  /**
   * See [Worker.doWork]
   */
  override suspend fun doWork(): Result {
    try {
      val response = loginRepository.getPermissions()
      val permissions = response.datum.mapNotNull {
        it.getTag()
      }
      if (permissions.isNotEmpty()) {
        val userPermissions =
          PermissionTag.values().map { it.param }.toSet().intersect(permissions).toList()
        loginRepository.updatePermissions(userPermissions)
      } else {
        loginRepository.removePermissions()
      }
    } catch (exception: HttpException) {
      removeDownloadPermission()
    } catch (exception: IOException) {
      removeDownloadPermission()
    }
    return Result.success()
  }

  private fun removeDownloadPermission() {
    loginRepository.updatePermissions(
      loginRepository.getPermissionsFromPrefs().filter {
        PermissionTag.fromParam(it).isDownloadPermissionTag()
      }
    )
  }

  /**
   * [VerifyDownloadWorker.Factory]
   */
  @AssistedInject.Factory
  interface Factory : ChildWorkerFactory

  companion object {

    private const val DOWNLOAD_WORKER_TAG: String = "downloads"

    private const val VERIFY_DOWNLOAD_INTERVAL = 7L

    /**
     * Queue verify download worker
     *
     * @param workManager WorkManager
     */
    fun queue(workManager: WorkManager) {
      val verifyDownloadWorkRequest =
        PeriodicWorkRequestBuilder<VerifyDownloadWorker>(
          VERIFY_DOWNLOAD_INTERVAL,
          TimeUnit.DAYS
        ).addTag(DOWNLOAD_WORKER_TAG)
          .build()
      workManager.enqueue(verifyDownloadWorkRequest)
    }
  }
}
