<template>
    <el-row type="flex" class="row-bg" justify="space-around">
        <el-col :span="6">
            <div class="grid-content bg-purple-light"><label>关注:</label><br><b>{{userinfo.subing}}</b></div>
        </el-col>
        <el-col :span="6">
            <div class="grid-content bg-purple"><label>粉丝:</label><br><b>{{userinfo.subbed}}</b></div>
        </el-col>
        <el-col :span="6">
            <div class="grid-content bg-purple"><label>文章:</label><br><b>{{userinfo.posts}}</b></div>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "InfoBar",
        props:['userinfo']
    }
</script>

<style scoped>
    .el-row {
        padding-top: 10px;
        padding-bottom: 10px;
    }
    .grid-content {
        text-align: center;
        font-size: 14px;
        cursor: pointer;
    }

    .el-row {
        border-top: 1px solid gainsboro;
        border-bottom: 1px solid gainsboro;
    }
</style>