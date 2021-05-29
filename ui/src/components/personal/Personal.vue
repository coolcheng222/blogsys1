<template>

    <el-row>
        <el-col :span="2"></el-col>
        <el-col :span="18">
            <img class="image" src="@/assets/bgc2.png"/>
            <div class="username2" v-if="!mySelf"><subbutton :uid="uid"></subbutton></div>
            <div class="username">{{userinfo.username}}</div>
            <user-span :info="userinfo"></user-span>
        </el-col>
        <el-col :span="2"></el-col>
    </el-row>

</template>

<script>
    import {UserInfo} from "../../global/clazz";
    import axios from "axios";
    import UserSpan from "./UserSpan";
    import Subbutton from "../sub/subbutton";

    export default {
        name: "Personal",
        // eslint-disable-next-line vue/no-unused-components
        components: {Subbutton, UserSpan},
        methods:{
            getUserInfo(){
                axios.get(`/userinfo/${this.$attrs.uid}`).then(data => {
                        if (data.data.errno === 0) {
                            return data.data;
                        } else {
                            return Promise.reject(data.data);
                        }
                    }, error => {
                        return Promise.reject(error.toString());
                    }
                ).then(
                    data => {
                        Object.assign(this.userinfo, data.extend);
                    }, error => {
                        console.log(error);
                    }
                );
            }
        },
        watch:{
            uid(){
                this.$router.go(0);
            }
        },
        computed:{
            uid(){
                return this.$attrs.uid;
            },
            mySelf(){
                return this.uid === this.$store.state.user.uid
            }
        },
        mounted() {
            this.getUserInfo();

        },
        data(){
            return {
                userinfo:new UserInfo()
            }
        }
    }
</script>

<style scoped>
    .image{
        /*margin-left: 10%;*/
        width: 100%;
        height: 200px;
    }
    .username{
        position: absolute;
        left: 10%;
        top: 152px;
        color: white;
        font-size: 40px;
        font-weight: bold;
    }
    .username2{
        position: absolute;
        left: 10%;
        top: 78px;
        color: white;
        font-size: 40px;
        font-weight: bold;
    }
    .sub{
        display: inline-block;
        margin-bottom: 10px;
        padding-bottom: 10px;
        height: 20px;
    }
</style>