<template>
    <el-container>
        <el-aside width="20px"><i :class="icon[trueIndex]"></i></el-aside>
        <el-main>
            <div class="content">{{item.content}}</div>
            <div class="title">文章: {{item.post.title}}</div>
        </el-main>
    </el-container>


</template>

<script>
    export default {
        name: "replyLi",
        props: [
            'item',
            'index'
        ],
        data(){
            return {
                icon: ['el-icon-trophy-1','el-icon-trophy','el-icon-medal']
            }
        },
        computed:{
            trueIndex(){
                return this.index >= 2? 2: this.index;
            }
        }
    }
</script>

<style scoped>
    div {
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        word-break: break-all;
        height: 20px;
    }

    .content {
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    }

    .title {
        color: #ada5a5;
        font-size: 13px;
    }
    i,.el-aside{
        font-size: 20px;
        display: flex;
        justify-content: center;
        text-align: center;
        line-height: 60px;
    }
    .el-main{
        padding-top: 10px;
        padding-bottom: 10px;
        height: 60px;
    }
</style>