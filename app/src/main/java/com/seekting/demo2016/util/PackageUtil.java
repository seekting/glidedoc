package com.seekting.demo2016.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 完成以下功能： 己安装程序，APK相关信息，包名，路径，证书公钥等读取；APK安装，卸载；文件打开
 *
 * @version v1.0
 *
 * @author neil lizhize
 */
public class PackageUtil {

    // 截断处理，防止恶意APP的超长label攻击
    private static final int APP_LABEL_MAX_LENGTH = 64;

    /**
     * 查询手机内所有支持分享的应用
     *
     * @param context
     * @return 支持分享的应用列表
     */
    public static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        return mApps;
    }

    public static boolean isSystemApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public static boolean isSystemUpdateApp(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
    }

    /**
     * 枚举出己安装的应用。
     *
     * @param context
     *            context句柄，读取安装包的信息。
     * @return List<PackageInfo> 己安装应用的列表。
     */
    public static List<PackageInfo> getInstallApp(Context context) {
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);
        int i = list.size() - 1;
        while (i > 0) {
            if (!PackageUtil.isAppLaunchable(context, list.get(i).packageName)) {
                list.remove(i);
            }
            i--;
        }
        return list;
    }

    public static boolean isAppLaunchable(Context context, String pkgName) {
        return null != context.getPackageManager().getLaunchIntentForPackage(pkgName);
    }

    public static boolean isPackageInstalled(Context context, String pkgName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            return new File(appInfo.publicSourceDir).exists();
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isAppInstalled(Context context, String pkgName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(pkgName);
    }

    public static Drawable getAppIcon(Context context, String pkgName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, 0);
            Drawable d = pkgInfo.applicationInfo.loadIcon(pm);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppLabel(Context context, String pkgName) {
        String label = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            label = pkgInfo.applicationInfo.loadLabel(pm).toString();
            if (TextUtils.isEmpty(label)) {
                label = pkgName;
            }
            if (!TextUtils.isEmpty(label) && label.length() > APP_LABEL_MAX_LENGTH) {
                label = label.substring(0, APP_LABEL_MAX_LENGTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return label;
    }

    public static PackageInfo getAppPackageInfo(Context context, String pkgName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable getApkIcon(Context context, String apkFilePath) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            appInfo.sourceDir = apkFilePath;
            appInfo.publicSourceDir = apkFilePath;
            return appInfo.loadIcon(pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getApkLabel(Context context, String apkFilePath) {
        String label = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            appInfo.sourceDir = apkFilePath;
            appInfo.publicSourceDir = apkFilePath;
            label = pm.getApplicationLabel(appInfo).toString();
            if (TextUtils.isEmpty(label)) {
                label = appInfo.packageName;
            }
            if (!TextUtils.isEmpty(label) && label.length() > APP_LABEL_MAX_LENGTH) {
                label = label.substring(0, APP_LABEL_MAX_LENGTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return label;
    }

    public static PackageInfo getApkPackageInfo(Context context, String apkFilePath) {
        PackageInfo pkgInfo = null;
        try {
            PackageManager pm = context.getPackageManager();
            pkgInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            appInfo.sourceDir = apkFilePath;
            appInfo.publicSourceDir = apkFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkgInfo;
    }

    public static int getVersionCode(Context context, String pkgName) {
        int versionCode = 1;
        PackageInfo pkgInfo = PackageUtil.getAppPackageInfo(context, pkgName);
        if (pkgInfo != null) {
            versionCode = pkgInfo.versionCode;
        }
        return versionCode;
    }

    public static String getVersionName(Context context, String pkgName) {
        String versionName = "1.0.0";
        PackageInfo pkgInfo = PackageUtil.getAppPackageInfo(context, pkgName);
        if (pkgInfo != null) {
            versionName = pkgInfo.versionName;
        }
        return versionName;
    }

    public static String getAppVersion(Context context, String pkgName) {
        String version = null;
        PackageInfo pkgInfo = PackageUtil.getAppPackageInfo(context, pkgName);
        if (pkgInfo == null) {
            version = null;
        } else {
            version = pkgInfo.versionCode + "/" + pkgInfo.versionName;
        }
        return version;
    }

    public static int getVersionCodeInt(String version) {
        if (TextUtils.isEmpty(version)) {
            return 0;
        }
        int index = version.indexOf('/');
        if (index == -1) {
            return 0;
        }
        int versionCode = Integer.parseInt(version.substring(0, index).trim());
        return versionCode;
    }

    public static void installApk(Context context, String apkFilePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(apkFilePath));
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unInstall(Context context, String pkgName) {
        Uri uri = Uri.fromParts("package", pkgName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startUp(Context context, String pkgName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
            if (intent == null) {
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setPackage(pkgName);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean startPackageLaunch(Context mContext, String pkgName) {
        try {
            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
