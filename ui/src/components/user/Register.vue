<template>
    <div class="outer">
        <div class="inner">
            <el-form :model="user" :rules="rules" @submit.prevent label-position="left" label-width="80px">
                <el-form-item label="用户名" prop="username">
                    <el-col :span="20">
                        <el-input  v-model="user.username" placeholder="5-18位英文或数字" clearable></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-col :span="20">
                        <el-input v-model="user.password" @blur="confirming" type="password" placeholder="5-18位英文或数字"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-col :span="20">
                        <el-input v-model="confirmPassword" @blur="confirming" type="password" placeholder="确认密码"></el-input>
                        <div class="el-form-item__error" v-show="isConfirm">两次输入密码不同</div>
                    </el-col>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-col :span="20">
                        <el-input v-model="user.email" placeholder="邮箱"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-col :span="10">
                        <el-input v-model="user.kaptcha" placeholder="验证码"></el-input>
                    </el-col>
                    <el-col :span="9">
                        <kaptcha></kaptcha>
                    </el-col>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="register" :disabled="!canReg">注册
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import kaptcha from "./kaptcha";
    import {User} from "@/global/clazz.js"
    import axios from "@/global/axiosConfig.js";
    import qs from 'qs';
    import {reactive} from "@vue/reactivity";

    export default {
        name: "Register",
        components: {
            kaptcha
        }, data() {
            let allOk = reactive({
                username: false,
                email: false,
                password: false
            })
            function validateUsername(rule,value,callback){
                if(/^[a-zA-Z0-9_]{5,18}$/.test(value)){
                    allOk.username = true;
                    callback();
                }else{
                    allOk.username = false;
                    callback(new Error("必须为5-18位字母或数字"))
                }
            }
            function validateUsername2(rule,value,callback){
                axios({
                    url: `user/${value}`,
                    method: `GET`,
                }).then(data=>{
                    if(data.data.errno == 0){
                        return Promise.resolve(data.data);

                    }else{
                        return Promise.reject(data.data);
                    }
                },error => {
                    callback(new Error(error));
                }).catch(error=>{
                    // console.log(error);
                    callback(new Error(error.message));
                })
            }
            function validatePassword(rule,value,callback){

                if(/^[a-zA-Z0-9_]{5,18}$/.test(value)){
                    allOk.password = true
                    callback();
                }else{
                    allOk.password = false
                    callback(new Error("必须为5-18位字母或数字"))
                }
            }
            function validateEmail(rule,value,callback){
                if(/^([a-z0-9_\\.-]+)@([\da-z\\.-]+)\.([a-z\\.]{2,6})$/.test(value)){
                    allOk.email = true;
                    callback();
                }else{
                    allOk.email = false;
                    callback(new Error("邮箱格式不对"));
                }
            }
            return {
                user: new User(),
                confirmPassword: "",
                isConfirm: false,
                allOk,

                rules: {
                    username: [
                        // eslint-disable-next-line no-undef
                        {required: true,validator: validateUsername, trigger: 'blur'},
                        {required: true,validator: validateUsername2, trigger: 'blur'}
                    ],
                    password: [
                        // eslint-disable-next-line no-undef
                        {required: true,validator: validatePassword, trigger: 'blur'}
                    ],
                    email: [
                        // eslint-disable-next-line no-undef
                        {required: true,validator: validateEmail, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            register() {
                if(this.confirm1()){
                    axios({
                        url: '/register',
                        method: 'post',
                        data: qs.stringify(this.user)
                    }).then(data=>{
                        if(data.data.errno == 1){
                            return Promise.reject(data.data);
                        }else{
                            return Promise.resolve(data.data);
                        }
                    }).then(data =>{
                        if(data.errno == 2){
                            this.$router.push(data.message);
                        }
                    },error =>{
                        this.$router.replace("/user/register");
                        console.log(error);
                    })
                }else{
                    this.isConfirm = true;
                }
            },
            confirm1(){
                if (this.user.password != this.confirmPassword) {
                    return false;
                }else{
                    return true;
                }
            },
            confirming(){
                this.isConfirm = false;
            }
        },
        computed:{
            canReg: function () {
                return this.allOk.username
                    && this.allOk.password && this.allOk.email
            }
        }
    }

</script>

<style scoped>
    .outer {
        height: 100%;
        background-color: rgba(44, 150, 154, 0);
        margin-top: 20px;
        /*border-top: red 2px solid;*/
        /*border-bottom: 1px #D9D9D9 solid;*/
        /*border-right: 1px #D9D9D9 solid;*/
        /*border-left: 1px #D9D9D9 solid;*/
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        /*border: 1px black solid;*/
    }

    .inner {
        padding: 20px;
        padding-top: 50px;
    }

    .el-form-item >>> .el-form-item__label {
        font-weight: bold;
        color: #2d2c68;
        font-size: 16px;
    }

    .el-button {
        width: 150px;
    }
</style>