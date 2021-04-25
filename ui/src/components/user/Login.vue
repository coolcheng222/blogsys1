<template>
    <div class="outer">
        <div class="inner">
            <el-form @submit.prevent label-position="left" label-width="70px">
                <el-form-item label="用户名">
                    <el-col :span="20">
                        <el-input v-model="username" placeholder="用户名/会员名" clearable></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="密码">
                    <el-col :span="20">
                        <el-input v-model="password" type="password" placeholder="密码"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-col :span="10">
                        <el-input v-model="kaptcha" placeholder="验证码"></el-input>
                    </el-col>
                    <el-col :span="9">

                        <kaptcha></kaptcha>
                    </el-col>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import kaptcha from "./kaptcha";
    // import axios from "axios";
    import axios from '@/global/axiosConfig.js';
    import qs from 'qs';
    export default {
        name: "Login",
        components:{
          kaptcha
        },
        mounted() {

        },
        data() {
            return {
                username: '',
                password: '',
                kaptcha: '',
            }
        },methods: {
            login(){
                var {username,password,kaptcha} = this;
                axios({
                    url: 'login',
                    method: "POST",
                    data: qs.stringify({
                        username,password,kaptcha
                    }),
                    header:{
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(data=>{
                    console.log(data);
                },msg=>{
                    console.log(msg);
                })
            }
        }
    }
</script>

<style scoped>
    .outer {
        margin-top: 70px;
        height: 70%;
        background-color: #E9E9F2;
        border-top: red 2px solid;
        border-bottom: 1px #D9D9D9 solid;
        border-right: 1px #D9D9D9 solid;
        border-left: 1px #D9D9D9 solid;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        /*border: 1px black solid;*/
    }
    .inner{
        padding: 24px;
        padding-top: 50px;
    }
    .el-form-item >>> .el-form-item__label{
        font-weight: bold;
    }
    .el-button{
        width: 150px;
    }
</style>