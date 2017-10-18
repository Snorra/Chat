这是一个聊天小程序.
在第一次上传这个项目到github上时，由于使用的是ssh协议，导致出现错误，
需要在github上设置ssh的key,
解决方法：使用命令git remote rm origin删除这个关联，然后再使用https关联，
最后再push代码上去。