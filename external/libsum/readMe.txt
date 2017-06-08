在jni的上一个目录里输入ndk-build命令，生成so文件

app的gradle里配了两个so库的目录所以不用再把生成的so文件拷到app/libs下
  jniLibs.srcDirs = ['libs', '../external/liba/libs']