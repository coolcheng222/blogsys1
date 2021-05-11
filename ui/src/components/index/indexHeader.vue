<template>

    <ul class="nav">
        <el-row>
            <el-col :span="3">
                <li>
                    <router-link to="/index" :class="'aaa'"><img style="height: 60px" src="@/assets/logo2.png">
                    </router-link>
                </li>
            </el-col>
            <el-col :span="14"></el-col>
            <el-col :span="3">
                <div class="wrap">
                    <el-input size="large" v-model="search" placeholder="搜索tag/博客/用户"></el-input>
                    <el-button type="primary" round><i class="el-icon-search"></i></el-button>
                </div>
            </el-col>
            <template v-if="!isLogin">
                <el-col :span="2"></el-col>
                <el-col :span="1">
                    <li class="hov" @click="toLogin">
                        <router-link to="#">登录</router-link>
                    </li>
                </el-col>
                <el-col :span="1">
                    <li class="hov" @click="$router.push('/register')">
                        <router-link to="#">注册</router-link>
                    </li>
                </el-col>
            </template>
            <template v-else>
                <el-col :span="4">


                    <el-popover trigger="hover" placement="bottom-end">
                        <el-menu>
                            <el-menu-item>个人中心</el-menu-item>
                            <el-menu-item @click="logout">登出</el-menu-item>
                        </el-menu>
                        <template #reference>
                            <li class="user">{{username}}<i class="el-icon-arrow-down"></i></li>
                        </template>
                    </el-popover>
                    <!--                    <div><a href="#" @click="logout">登出</a></div>-->
                </el-col>
            </template>
        </el-row>

    </ul>


</template>

<script>
    import {mapState} from "vuex";
    import axios from '@/global/axiosConfig.js';

    export default {
        name: "indexHeader",
        data() {
            return {
                search: ''
            }
        },
        methods: {
            toLogin() {
                this.$router.push("/login");
            }, logout() {
                axios.post('logout')
                    .then(data => {
                        console.log(data);
                    }, error => {
                        console.log(error);
                    });
                this.$store.dispatch('logout');
            }
        }, computed: {
            ...mapState({
                isLogin: state => state.user.isLogin,
                username: state => state.user.username
            })
        }
    }
</script>

<style scoped>
    .nav {
        list-style: none;
        padding-right: 40px;
        padding-left: 20px;
        background-color: #e6e6e6;
        border-bottom: 1px solid rgb(230, 230, 230);
        height: 50px;
    }

    .nav li {
        line-height: 50px;
        text-align: center;
        border-radius: 25px;
        cursor: pointer;

    }

    li >>> a {
        text-decoration: none;
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

    .wrap {
        background: rgba(255, 255, 255, 0.4);
        position: absolute;
        top: 32%;
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

    .hov:hover {
        background-color: #eff5bb;
    }

    .hov:active {
        background-color: #cb0eff;
    }

    img {
        position: relative;
        top: -5%;
        left: 0;
    }
    .user{
        font-size: 16px;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    }
    .el-menu{
        border-right: none;
    }
    .el-popover{
        background-color: #000;
    }
    .el-popover .el-menu-item{
        text-align: center;
    }
</style>