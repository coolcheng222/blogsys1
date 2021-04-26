<template>
    <el-header>
        <el-row type="flex">
            <el-col :span="2"><router-link to="/index" :class="'aaa'"><img style="height: 60px" src="@/assets/logo2.png"></router-link></el-col>
            <el-col :span="3" class="signal">用户{{msg}}</el-col>
        </el-row>
    </el-header>
</template>

<script>
    export default {
        name: "UserHeader",
        props:{
            theme: String,
        },
        data(){
            return {
                logo: 'todo2.jpg',
            }
        },
        computed:{
            // eslint-disable-next-line vue/return-in-computed-property
            msg(){
                if(this.theme === 'Login'){
                    return "登录";
                }else if(this.theme === 'Register'){
                    return "注册"
                }
                return "a";
            }
        }
    }
</script>

<style scoped>
    .el-header{
        border-bottom: #E7E7E7 1px solid;
        height: 10px;
        margin-top: 0;
        background-image: url("~@/assets/bgc.png");
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
    }
    .signal{
        /*text-align: center;*/
        font-size: 20px;
        font-weight: bold;
        display: flex;
        align-items: center;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        /*transform:translateY(-50%);*/
    }
</style>
