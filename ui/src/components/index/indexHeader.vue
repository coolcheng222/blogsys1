<template>
    <!--disable-eslint-->
    <el-affix :offset="0">
        <div class="header">
            <ul class="nav">
                <el-row >
                    <el-col :span="3">
                        <logo @toggle="toggle" :showing="showing"></logo>
                    </el-col>
                    <el-col :span="14">

                            <transition name="some">
                                <div v-if="showing">
                                        <div class="navnav"><router-link to="/index">主页</router-link></div>
                                        <div class="navnav"><router-link to="/hot">热门</router-link></div>
                                </div>

                            </transition>

                    </el-col>
                    <el-col :span="3">
                        <searchbox></searchbox>
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


                            <el-popover trigger="hover" popper-class="userpop" placement="bottom-end" :width="240">
                                    <user-info1></user-info1>
                                <template #reference>
                                    <li class="user">{{username}}<i class="el-icon-arrow-down"></i></li>
                                </template>
                            </el-popover>
                            <!--                    <div><a href="#" @click="logout">登出</a></div>-->
                        </el-col>
                    </template>
                </el-row>

            </ul>
        </div>

    </el-affix>

</template>

<script>
    import {mapState} from "vuex";
    import UserInfo1 from "../user/info/UserInfo1";
    import Searchbox from "./searchbox";
    import Logo from "./logo";


    export default {
        name: "indexHeader",
        // eslint-disable-next-line vue/no-unused-components
        components: {Logo, Searchbox, UserInfo1},
        data() {
            return {
                showing: false
            }
        },
        mounted() {

        },
        methods: {
            toLogin() {
                this.$router.push("/login");
            }, logout() {

                this.$store.dispatch('logout');
            },
            toggle(){
                this.showing = !this.showing
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
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
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


    .el-input {
        width: 70%;
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


    .user {
        font-size: 16px;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    }

    .el-menu {
        border-right: none;
    }

    .el-popover >>> .is-light{
        background-color: #000;
        padding: 0
    }

    /*.el-popover .el-menu-item {*/
    /*    text-align: center;*/
    /*}*/

    .header {
        /*position: fixed;*/
    }
    .navnav{
        height: 42px;
        float: left;
        width: 50px;
        text-decoration: none;
        margin-top: 8px;
    }
    .navnav a{
        text-decoration: none;
        color: black;
        line-height: 50px;
        border-bottom: 3px solid darkseagreen;
        font-size: 17px;
    }
    .navnav a:hover{
        border-bottom: 3px solid yellowgreen;
        color: red;
    }
    .el-row{
        height: 50px;
    }
    .some-enter-active {
        transition: all .3s ease-out;
    }

    .some-leave-active {
        transition: all .3s cubic-bezier(0.8, 0.5, 1.0, 1.0);
    }

    .some-enter-from,
    .some-leave-to {
        transform: translateX(-20px);
        opacity: 0;
    }

</style>