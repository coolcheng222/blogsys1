<template>
    <div v-if="isLogin">
        <div class="inputing">

            <el-input type="textarea" v-model="reply.content" placeholder="在这里发评论"></el-input>
            <el-button @click="postReply" :disabled="!loaded">发表</el-button>
        </div>
    </div>
    <div v-else>
        <div class="prompt">游客,想要评论请先<span @click="toLogin" class="login-prop">登录</span></div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import {Reply} from "../../global/clazz";
    import axios from 'axios'

    export default {
        name: "writereply",
        props:['post','loaded'],
        data(){
            return {
                reply: new Reply()
            }
        },
        computed:{
            ...mapState({
                'isLogin': state=>state.user.isLogin,
                'uid': state=>state.user.uid
            })
        },
        methods:{
            toLogin(){
                this.$store.dispatch('toLogin',this.$route.fullPath);
                this.$router.push('/login');
            },
            postReply(){
                this.reply.user = {};
                this.reply.post = this.post;
                this.reply.user.uid = this.uid;
                axios.post(`/reply`,this.reply);
            }
        },
    }
</script>

<style scoped>
    .prompt{
        margin-left: 10px;
        margin-right: 40px;
        height: 68px;
        margin: 0 40px 0 0px;
        text-align: center;
        line-height: 68px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        border-radius: 2px;
        background-color: #fff;
    }
    .login-prop{
        color: cornflowerblue;
        text-decoration: underline;
        cursor: pointer;
    }
    .inputing{
        margin-right: 50px;
        margin-left: 10px;
        height: 131px;
    }
    .inputing >>> .el-textarea__inner{
        resize: none;
        height: 131px;
    }
    .el-button{
        float: right;
        margin-top: 10px;
        margin-right: 20px;
    }
</style>