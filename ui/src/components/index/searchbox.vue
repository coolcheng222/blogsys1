<template>
    <div class="wrap">
        <el-input size="large" v-model="search" placeholder="搜索tag/博客/用户"></el-input>
        <el-button type="primary" round><i class="el-icon-search"></i></el-button>
    </div>
</template>

<script>
    export default {
        name: "searchbox",
        data(){
            return{
                search: ''
            }
        }
    }
</script>

<style scoped>

    .wrap {
        background: rgba(255, 255, 255, 0.4);
        position: absolute;
        top: 50%;
        left: 64%;
        transform: translate(-50%, -50%);
        -webkit-transform: translate(-50%, -50%);
        -moz-transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        -o-transform: translate(-50%, -50%);
        width: 420px;
        height: 44px;
        border-radius: 24px;
    }
    .el-input >>> input {
        width: 310px;
        height: 22px;
        font-size: 16px;
        line-height: 22px;
        float: left;
        margin-left: 20px;
        margin-top: 11px;
        color: #6f6565;
        background: none;
        border: none;
    }


    .el-input {
        width: 70%;
    }
    .el-button {
        margin: 8px 0;
        float: right;
        position: absolute;
        top: -12%;
        left: 84%;
    }
</style>