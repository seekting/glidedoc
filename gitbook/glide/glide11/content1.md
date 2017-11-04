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

onSizeReady之后会启动engine.load()
```java
 public <T, Z, R> LoadStatus load(Key signature, int width, int height
           //...ignore code
              DiskCacheStrategy diskCacheStrategy, ResourceCallback cb) {
    
      
        EngineKey key =//...ignore code

        //如果要用内存缓存就尝试获取内存缓存
        EngineResource<?> cached = loadFromCache(key, isMemoryCacheable);
        if (cached != null) {
            cb.onResourceReady(cached);
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                logWithTimeAndKey("Loaded resource from cache", startTime, key);
            }
            return null;
        }
        //尝试从正在使用的缓存里找
        EngineResource<?> active = loadFromActiveResources(key, isMemoryCacheable);
        if (active != null) {
            cb.onResourceReady(active);
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                logWithTimeAndKey("Loaded resource from active resources", startTime, key);
            }
            return null;
        }

        //查看有没有正在处理请求的job，请求排重的处理
        EngineJob current = jobs.get(key);
        if (current != null) {
            current.addCallback(cb);
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                logWithTimeAndKey("Added to existing load", startTime, key);
            }
            return new LoadStatus(cb, current);
        }

        EngineJob engineJob = engineJobFactory.build(key, isMemoryCacheable);
        //diskCacheStrategy被传到DecodeJob里
        DecodeJob<T, Z, R> decodeJob = new DecodeJob<T, Z, R>(key, width, height, fetcher, loadProvider, transformation,
                transcoder, diskCacheProvider, diskCacheStrategy, priority);
        EngineRunnable runnable = new EngineRunnable(engineJob, decodeJob, priority);
        jobs.put(key, engineJob);
        engineJob.addCallback(cb);
        //开启任务
        engineJob.start(runnable);
        
        return new LoadStatus(cb, engineJob);
    }

```

EngineRunnable里就是decode()
```java
class EngineRunnable implements Runnable, Prioritized {
    
    @Override
    public void run() {
        if (isCancelled) {
            return;
        }

        Exception exception = null;
        Resource<?> resource = null;
        try {
            resource = decode();
        } catch (Exception e) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Exception decoding", e);
            }
            exception = e;
        }
    }
}



decode先去从缓存里取

  
```
```java
private Resource<?> decode() throws Exception {
    if (isDecodingFromCache()) {
        return decodeFromCache();
    } else {
        return decodeFromSource();
    }
}

```

先从result里取，再从source里取
```java
private Resource<?> decodeFromCache() throws Exception {
    Resource<?> result = null;
    try {
        result = decodeJob.decodeResultFromCache();
    } catch (Exception e) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Exception decoding result from cache: " + e);
        }
    }

    if (result == null) {
        result = decodeJob.decodeSourceFromCache();
    }
    return result;
}

```
从result里取的时候如果diskCacheStrategy不缓存结果直接返回

```java
public Resource<Z> decodeResultFromCache() throws Exception {
    if (!diskCacheStrategy.cacheResult()) {
        return null;
    }

 //...ignore code
}

```
如果不缓存Source则返回空
```java
public Resource<Z> decodeSourceFromCache() throws Exception {
    if (!diskCacheStrategy.cacheSource()) {
        return null;
    }

//...ignore code
}

```
到此缓存策略的内容讲完了

