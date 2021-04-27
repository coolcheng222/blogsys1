<template>
    <el-container :style="{backgroundImage: `url('${img}'`}">
        <UserHeader :theme="theme"></UserHeader>
        <el-row type="flex" justify="space-between">
            <el-col :span="layOut[0]">
                <div :class="theme" class="pic">

                </div>
            </el-col>
            <el-col :span="layOut[1]">
                <router-view></router-view>
            </el-col>
            <el-col :span="layOut[2]"></el-col>
        </el-row>
        <el-footer></el-footer>
    </el-container>
</template>

<script>
    import UserHeader from "./UserHeader";
    import {mapState} from "vuex";

    export default {
        name: "User",
        components: {
            UserHeader
        }, data() {
            return {
                theme: "Login",

            }
        },
        mounted() {
            if (this.$route.fullPath.match("login")) {
                this.theme = "Login";
            } else if (this.$route.fullPath.match("register")) {
                this.theme = "Register";
            }
        },
        computed:{
            img(){
                return require('@/assets/' + this.theme + '.jpg' );
            },
            layOut(){
                if(this.theme === "Login"){
                    return [9,7,8];
                }else if(this.theme === "Register"){
                    return [14,8,2];
                }
                return [];
            },
            ...mapState({
                isLogin: 'isLogin'
            })
        },
        beforeRouteEnter (to, from, next) {
            next(vm=>{
                console.log(vm);
                if(vm.$store.state.isLogin){
                    vm.$store.dispatch('fromLogin');
                    vm.$router.push("/index");
                }
            })
        },
        beforeRouteUpdate(to){
            // console.log(to);
            if(this.isLogin){
                this.$store.dispatch('fromLogin',this.$router);
            }
            if (to.fullPath.match("login")) {
                this.theme = "Login";
            } else if (to.fullPath.match("register")) {
                this.theme = "Register";
            }
        }
    }
</script>

<style scoped>
    .el-container {
        background-color: #535A60;
        height: 100%;
        /*background-image: url("/Login.jpg");*/
        background-repeat: no-repeat;
        background-size: cover;
    }

    .el-row {
        height: 100%;
    }
    .Login{

    }
    .el-footer{
        height: 10px;
    }

</style>