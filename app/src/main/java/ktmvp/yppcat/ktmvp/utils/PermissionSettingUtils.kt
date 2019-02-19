package ktmvp.yppcat.ktmvp.utils

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast

object PermissionSettingUtils {
    @JvmStatic
    fun jumpPermissionPage(mContext: Context) {
        val name = Build.MANUFACTURER
        when (name) {
            "HUAWEI" -> goHuaWeiMainager(mContext)
            "vivo" -> goVivoMainager(mContext)
            "OPPO" -> goOppoMainager(mContext)
            "Coolpad" -> goCoolpadMainager(mContext)
            "Meizu" -> goMeizuMainager(mContext)
            "Xiaomi" -> goXiaoMiMainager(mContext)
            "samsung" -> goSangXinMainager(mContext)
            "Sony" -> goSonyMainager(mContext)
            "LG" -> goLGMainager(mContext)
            else -> goIntentSetting(mContext)
        }
    }

    private fun goLGMainager(mContext: Context) {
        try {
            val intent = Intent(mContext.packageName)
            val comp = ComponentName("com.android.settings", "com.android.settings.Settings\$AccessLockSummaryActivity")
            intent.component = comp
            mContext.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            goIntentSetting(mContext)
        }

    }

    private fun goSonyMainager(mContext: Context) {
        try {
            val intent = Intent(mContext.packageName)
            val comp = ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity")
            intent.component = comp
            mContext.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            goIntentSetting(mContext)
        }

    }

    private fun goHuaWeiMainager(mContext: Context) {
        try {
            val intent = Intent(mContext.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")
            intent.component = comp
            mContext.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(mContext, "跳转失败", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            goIntentSetting(mContext)
        }

    }

    private fun goXiaoMiMainager(mContext: Context) {
        try {
            val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
            intent.putExtra("extra_pkgname", mContext.packageName)
            mContext.startActivity(intent)
        } catch (e: java.lang.Exception) {
            try {
                val newIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                newIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
                newIntent.putExtra("extra_pkgname", mContext.packageName)
                mContext.startActivity(newIntent)
            } catch (e: java.lang.Exception) {
                goIntentSetting(mContext)
            }
        }
    }

    private fun goMeizuMainager(mContext: Context) {
        try {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", mContext.packageName)
            mContext.startActivity(intent)
        } catch (localActivityNotFoundException: ActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace()
            goIntentSetting(mContext)
        }

    }

    private fun goSangXinMainager(mContext: Context) {
        //三星4.3可以直接跳转
        goIntentSetting(mContext)
    }

    private fun goIntentSetting(mContext: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", mContext.packageName, null)
        intent.data = uri
        try {
            mContext.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun goOppoMainager(mContext: Context) {
        doStartApplicationWithPackageName("com.coloros.safecenter", mContext)
    }

    /**
     * doStartApplicationWithPackageName("com.yulong.android.security:remote")
     * 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
     * startActivity(open);
     * 本质上没有什么区别，通过Intent open...打开比调用doStartApplicationWithPackageName方法更快，也是android本身提供的方法
     */
    private fun goCoolpadMainager(mContext: Context) {
        doStartApplicationWithPackageName("com.yulong.android.security:remote", mContext)
        /*  Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        startActivity(openQQ);*/
    }

    private fun goVivoMainager(mContext: Context) {
        doStartApplicationWithPackageName("com.bairenkeji.icaller", mContext)
        /*   Intent openQQ = getPackageManager().getLaunchIntentForPackage("com.vivo.securedaemonservice");
        startActivity(openQQ);*/
    }

    private fun doStartApplicationWithPackageName(packagename: String, mContext: Context) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        var packageinfo: PackageInfo? = null
        try {
            packageinfo = mContext.packageManager.getPackageInfo(packagename, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (packageinfo == null) {
            return
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(packageinfo.packageName)
        // 通过getPackageManager()的queryIntentActivities方法遍历
        val resolveinfoList = mContext.packageManager
                .queryIntentActivities(resolveIntent, 0)
        Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size)
        for (i in resolveinfoList.indices) {
            Log.e("PermissionPageManager", resolveinfoList[i].activityInfo.packageName + resolveinfoList[i].activityInfo.name)
        }
        val resolveinfo = resolveinfoList.iterator().next()
        if (resolveinfo != null) {
            // packageName参数2 = 参数 packname
            val packageName = resolveinfo.activityInfo.packageName
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
            val className = resolveinfo.activityInfo.name
            // LAUNCHER Intent
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            // 设置ComponentName参数1:packageName参数2:MainActivity路径
            val cn = ComponentName(packageName, className)
            intent.component = cn
            try {
                mContext.startActivity(intent)
            } catch (e: Exception) {
                goIntentSetting(mContext)
                e.printStackTrace()
            }

        }
    }
}