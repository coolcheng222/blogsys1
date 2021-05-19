<template>
    <div class="outer">
        <div class="inner">
            <div class="msg">{{message}}</div>
            <el-dialog
                    v-model="visible"
                    width="40%"
                    :close-on-click-modal="false"
                    :show-close="false"
                    :close-on-press-escape="false"
            >
                <div><i class="el-icon-loading"/><br> 登录中...</div>
<!--                <el-progress v-if="visible" :percentage="40" :format="()=>''" :indeterminate="true"></el-progress>-->
            </el-dialog>
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
                key: 1,
                visible: false
            }
        }, methods: {
            login() {
                if(this.$store.state.isLogin){
                    this.message = "请勿重复登录";
                }
                this.message = '';
                this.visible = true;
                let promise = this.$store.dispatch("login", this.user);
                // console.log(promise);
                promise.then(
                    // eslint-disable-next-line no-unused-vars
                    data => {
                        // console.log(data);
                        this.$router.push('/index');
                    },error => {
                        // console.log(error);
                        this.message = error;
                        this.user.password = '';
                        this.user.kaptcha = '';
                        this.updateKaptcha();
                    }
                ).finally(()=>{
                    this.visible = false;
                })
            },
            updateKaptcha() {
                this.key = Math.random();
            }
        },
        computed: {
            hasMessage() {
                return this.message.length >= 1;
            },
            ...mapState({
                beforePath: state => state.loginPage.beforePath
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
            // console.log(this.beforePath);
            if(from.name === 'login' && to.name !== 'register'){
                if(this.beforePath === to.path){
                    this.$store.dispatch('fromLogin');
                    next();
                }else{
                    var obj = {path: this.beforePath};
                    console.log(obj)
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
    .el-dialog div{
        font-size: 15px;
        text-align: center;
    }
</style>