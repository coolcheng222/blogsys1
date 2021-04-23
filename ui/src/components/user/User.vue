<template>
    <el-container :style="{backgroundImage: `url('${img}'`}">
        <UserHeader :theme="theme"></UserHeader>
        <el-row type="flex" justify="space-between">
            <el-col :span="9">
                <div :class="theme" class="pic">

                </div>
            </el-col>
            <el-col :span="7">
                <router-view></router-view>
            </el-col>
            <el-col :span="8"></el-col>
        </el-row>
        <el-footer></el-footer>
    </el-container>
</template>

<script>
    import UserHeader from "./UserHeader";

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

</style>