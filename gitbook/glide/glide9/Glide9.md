# Glide GlideModule个性化Glide

Glide可以通过GlideModule去个性化它的一些属性，它的做法值得学习。<br/>
我们写SDK的时候，通常是在sdk初始化的时候（Application.onCreate的时候）传参数进去完成;而Glide与众不同的是，通过meta-data指定一个类，操作builder从而达到目的。
这样做的好处是，你不知道Glide对象在何时需要初始化，但是只要初始化的时候，就一定会通过配置好的GlideModule对象去个性化Glide
还有，Glide的构造采用builder模式。


来看看Glide的代码

```java
public static Glide get(Context context) {
    if (glide == null) {
      synchronized (Glide.class) {
        if (glide == null) {

            Context applicationContext = context.getApplicationContext();
             //创建manifestParser去解析meta-data
            List<GlideModule> modules =
            new ManifestParser(applicationContext).parse();

            //创建builder
            GlideBuilder builder = new GlideBuilder(applicationContext);
            for (GlideModule module : modules) {
            //GlideModule去个性化builder
                module.applyOptions(applicationContext, builder);
            }
            //创建出想要的glide
            glide = builder.createGlide();
            for (GlideModule module : modules) {
            //注册一些想要的组件
    //例如:glide.register(Model.class, Data.class, new MyModelLoader());
                module.registerComponents(applicationContext, glide);
            }
        }
      }
    }

    return glide;
}

```

```java
public List<GlideModule> parse() {
    List<GlideModule> modules = new ArrayList<GlideModule>();
    try {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
        if (appInfo.metaData != null) {
        //遍历所有的meta-data，然后找出value=GlideModule的className
            for (String key : appInfo.metaData.keySet()) {
                if (GLIDE_MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                    modules.add(parseModule(key));
                }
            }
        }
   //...ignore code
    return modules;
}

```

```java
private static GlideModule parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
       //...ignore code

        Object module;
        //通过反射实例化对象
        try {
            module = clazz.newInstance();
       //...ignore code
        return (GlideModule) module;
    }

```

> 从Glide的实例化之前和之后都可以注入一些个性化的东西，实例化前可以个性化bitmapPool,MemoryCache等等；实例化后可以通过register注册loader。
> 这个思想值得学习。

