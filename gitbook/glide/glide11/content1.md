# Glide Disk缓存策略(下)

## 跟踪diskCacheStrategy的底层处理

diskCacheStrategy首先被放入Builder当中，后面经历了多少逻辑，最终实现它的意图呢？我们一步一步分析。

### Request中的diskCacheStrategy

```java

public final class GenericRequest<A, T, Z, R> implements //...ignore code{
        //...ignore code
    private Key signature;
    private Drawable fallbackDrawable;
    private int fallbackResourceId;
    private int placeholderResourceId;
    private int errorResourceId;
    private Context context;
    private Transformation<Z> transformation;
    private LoadProvider<A, T, Z, R> loadProvider;
    private RequestCoordinator requestCoordinator;
    private A model;
    private Class<R> transcodeClass;
    private boolean isMemoryCacheable;
    private Priority priority;
    private Target<R> target;
    private RequestListener<? super A, R> requestListener;
    private float sizeMultiplier;
    private Engine engine;
    private GlideAnimationFactory<R> animationFactory;
    private int overrideWidth;
    private int overrideHeight;
    private DiskCacheStrategy diskCacheStrategy;
```

request在初始化的时候会通过缓存策略来检查相应的decoder及encoder为不为空

```java
 private void init(){
if (diskCacheStrategy.cacheSource()) {
             check("SourceEncoder", loadProvider.getSourceEncoder(),
                     "try .sourceEncoder(Encoder) or .diskCacheStrategy(NONE/RESULT)");
         } else {
             check("SourceDecoder", loadProvider.getSourceDecoder(),
                     "try .decoder/.imageDecoder/.videoDecoder(ResourceDecoder) or .diskCacheStrategy(ALL/SOURCE)");
         }
         if (diskCacheStrategy.cacheSource() || diskCacheStrategy.cacheResult()) {
             // TODO if(resourceClass.isAssignableFrom(InputStream.class) it is possible to wrap sourceDecoder
             // and use it instead of cacheDecoder: new FileToStreamDecoder<Z>(sourceDecoder)
             // in that case this shouldn't throw
             check("CacheDecoder", loadProvider.getCacheDecoder(),
                     "try .cacheDecoder(ResouceDecoder) or .diskCacheStrategy(NONE)");
         }
         if (diskCacheStrategy.cacheResult()) {
             check("Encoder", loadProvider.getEncoder(),
                     "try .encode(ResourceEncoder) or .diskCacheStrategy(NONE/SOURCE)");
         }
}
            
private static void check(String name, Object object, String suggestion) {
    if (object == null) {
        StringBuilder message = new StringBuilder(name);
        message.append(" must not be null");
        if (suggestion != null) {
            message.append(", ");
            message.append(suggestion);
        }
        throw new NullPointerException(message.toString());
    }
}
```

然后通过OnSizeReady去启动引擎，对启动引擎的逻辑不熟悉的可以参考:
[Glide into()都做了什么](../glide5/Glide5.md) 