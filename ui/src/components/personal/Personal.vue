<template>

    <el-row>
        <el-col :span="2"></el-col>
        <el-col :span="18">
            <img class="image" src="@/assets/bgc2.png"/>
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

    export default {
        name: "Personal",
        components: {UserSpan},
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
</style>