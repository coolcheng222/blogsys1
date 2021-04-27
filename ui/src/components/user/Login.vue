<template>
    <div class="outer">
        <div class="inner">
            <div class="msg">{{message}}</div>
            <el-form @submit.prevent label-position="left" label-width="70px">
                <el-form-item label="用户名">
                    <el-col :span="20">
                        <el-input v-model="user.username" placeholder="用户名/会员名" clearable></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="密码">
                    <el-col :span="20">
                        <el-input v-model="user.password" type="password" placeholder="密码"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-col :span="10">
                        <el-input v-model="user.kaptcha" placeholder="验证码"></el-input>
                    </el-col>
                    <el-col :span="9">

                        <kaptcha :key="key"></kaptcha>
                    </el-col>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
                <router-link to="/user/register">去注册</router-link>
            </el-form>
        </div>
    </div>
</template>

<script>
    import kaptcha from "./kaptcha";
    // import axios from "axios";
    import axios from '@/global/axiosConfig.js';
    import qs from 'qs';
    import {User} from "../../global/clazz";
    import {mapState} from "vuex";

    export default {
        name: "Login",
        components: {
            kaptcha
        },
        mounted() {

        },
        data() {
            return {
                user: new User(),
                message: '',
                key: 1
            }
        }, methods: {
            login() {
                if(this.$store.state.isLogin){
                    this.message = "请勿重复登录";
                }
                this.message = '';
                var {username, password, kaptcha} = this.user;
                axios({
                    url: 'login',
                    method: "POST",
                    data: qs.stringify({
                        username, password, kaptcha
                    }),
                    header: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(data => {
                    if (data.data.errno === 1) {
                        return Promise.reject(data.data.message);
                    } else {
                        return data.data;
                    }
                }, msg => {
                    return Promise.reject(msg.toString());
                }).then(data => {
                    this.$store.dispatch("login", data.message);
                    console.log("aaa");
                    this.$router.push('/index');
                }, error => {
                    this.message = error;
                    this.user.password = '';
                    this.user.kaptcha = '';
                    this.updateKaptcha();
                })
            },
            updateKaptcha() {
                this.key++;
            }
        },
        computed: {
            hasMessage() {
                return this.message.length >= 1;
            },
            ...mapState({
                beforePath: 'beforePath'
            })
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                if (from.name !== 'register') {
                    vm.$store.dispatch('toLogin', from.path);
                }
            })

        },
        beforeRouteUpdate(){
            this.updateKaptcha();
        },
        beforeRouteLeave(to,from,next){
            if(from.name === 'login' && to.name !== 'register'){
                // console.log(from)
                // console.log(to);
                if(this.beforePath === to.path){
                    this.$store.dispatch('fromLogin');
                    next();
                }else{
                    var obj = {path: this.beforePath};
                    next(obj);
                }
                // console.log(obj)

            }else{
                next();
            }
        }
    }
</script>

<style scoped>
    .outer {
        margin-top: 70px;
        height: 70%;
        width: 343px;
        background-color: #E9E9F2;
        /*border-top: red 2px solid;*/
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);

        /*border: 1px black solid;*/
    }

    .inner {
        padding: 24px;
        padding-top: 10px;
    }

    .el-form-item >>> .el-form-item__label {
        font-weight: bold;
    }

    .el-button {
        width: 150px;
    }

    .msg {
        margin: 15px auto;
        text-align: center;
        color: red;
        height: 18px;
        width: 100%;
    }
</style>