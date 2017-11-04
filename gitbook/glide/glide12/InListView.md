# Glide cancel初步探究

## 前言
如果两个url先后绑定到一个ImageView上：url1先,url2后绑定，url1要3秒返回，url2要1秒就返回;<br>
那结果会不会是显示url1呢？答案并不是这样，它依然是显示url2；<br>
要做到这一点其实不难，只要bitmap返回的时候，和最后一个url对比，如果不相同就抛弃<br>
Glide在这点上做的比我们想像的高明。

## 如果没有Glide我们会怎么做