# Glide cancel初步探究

## 前言
如果两个url先后绑定到一个ImageView上：url1先,url2后绑定，url1要3秒返回，url2要1秒就返回;<br>
那结果会不会是显示url1呢？答案并不是这样，它依然是显示url2；<br>
要做到这一点其实不难，只要bitmap返回的时候，和最后一个url对比，如果不相同就抛弃<br>
Glide在这点上做的比我们想像的高明。

## 如果没有Glide我们会怎么做

```java
public class MyImageView extends ImageView implements OnBitmapLoadedCallBack{
    private String url;
    //...ignore code
    
    public void setUrl(String url){
        checkInUiThread();
        
        if(!this.url.equals(url)){
        this.url=url;
        //异步加载数据
        mLoader.load(url,this);
        }
    }
    public void onBitmapLoaded(String url,Bitmap b){
        if(this.url.equals(url)){
            setBitmapDrawable(b);
        }else{
            //...ignore bitmap
        }
    }
}

```
上述代码大家应该不会陌生；该实现确实能保证最后会显示用户想要的bitmap：因为setUrl是在一个线程里执行。

但是它有一个缺点：换url的时候，之前的url加载可以取消，于是我们可以对它进行优化:
```java
public class MyImageView extends ImageView implements OnBitmapLoadedCallBack{
    private String url;
    private ImageLoader mLoader;
    //...ignore code
    
    public void setUrl(String url){
        checkInUiThread();
        
        if(!this.url.equals(url)){
        mLoader.cancel(url);
        this.url=url;
        //异步加载数据
        mLoader.load(url,this);
        }
    }
    public void onBitmapLoaded(String url,Bitmap b){
        if(this.url.equals(url)){
            setBitmapDrawable(b);
        }else{
            //...ignore bitmap
        }
    }
}
```
## Glide 是怎么做到的

来看看它的实现：

into的时候build一个ViewTarget
```java
 public Target<TranscodeType> into(ImageView view) {
        Util.assertMainThread();
        if (view == null) {
            throw new IllegalArgumentException("You must pass in a non null View");
        }

        if (!isTransformationSet && view.getScaleType() != null) {
            switch (view.getScaleType()) {
                case CENTER_CROP:
                    applyCenterCrop();
                    break;
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                    applyFitCenter();
                    break;
                //$CASES-OMITTED$
                default:
                    // Do nothing.
            }
        }

        return into(glide.buildImageViewTarget(view, transcodeClass));
    }


```

new ViewTarget 
```java
public <Z> Target<Z> buildTarget(ImageView view, Class<Z> clazz) {
    if (GlideDrawable.class.isAssignableFrom(clazz)) {
        return (Target<Z>) new GlideDrawableImageViewTarget(view);
    } else if (Bitmap.class.equals(clazz)) {
        return (Target<Z>) new BitmapImageViewTarget(view);
    } else if (Drawable.class.isAssignableFrom(clazz)) {
        return (Target<Z>) new DrawableImageViewTarget(view);
    } else {
        throw new IllegalArgumentException("Unhandled class: " + clazz
                + ", try .as*(Class).transcode(ResourceTranscoder)");
    }
}

```


```java
public <Y extends Target<TranscodeType>> Y into(Y target) {
    Util.assertMainThread();
   //...ignore code
    //第一次target里的requset为空
    Request previous = target.getRequest();

    //如果前面有Requset就将它取消
    if (previous != null) {
        previous.clear();
        requestTracker.removeRequest(previous);
        previous.recycle();
    }

    Request request = buildRequest(target);
    target.setRequest(request);
    lifecycle.addListener(target);
    requestTracker.runRequest(request);

    return target;
}

```

来看看setRequest,最终的实现是通过tag和view绑定

```java
@Override
public void setRequest(Request request) {
    //调用setTag
    setTag(request);
}

private void setTag(Object tag) {
    //通过setTag把View和Request绑定
    if (tagId == null) {
        isTagUsedAtLeastOnce = true;
        view.setTag(tag);
    } else {
    //也可以通过用户自定义的方式去setTag
        view.setTag(tagId, tag);
    }
}

public static void setTagId(int tagId) {
    if (ViewTarget.tagId != null || isTagUsedAtLeastOnce) {
        throw new IllegalArgumentException("You cannot set the tag id more than once or change"
            + " the tag id after the first request has been made");
    }
    ViewTarget.tagId = tagId;
}

```

当一个newTarget调用getRequest的时候它其实是通过view的tag拿到前驱
```java
@Override
public Request getRequest() {
    Object tag = getTag();
    Request request = null;
    if (tag != null) {
        if (tag instanceof Request) {
            request = (Request) tag;
        } else {
            throw new IllegalArgumentException("You must not call setTag()" +
             " on a view Glide is targeting");
        }
    }
    return request;
}

```
## Glide的实现与其它实现对比
1. 不用extends ImageView
1. 不用关心什么时候cancel什么时候load
1. 不用记住最后一个url是什么

# 完结
