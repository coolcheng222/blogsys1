<template>
    <el-row>
        <el-col :span="6">
            <user-info2 :post="post" :loaded="loaded"></user-info2>
        </el-col>
        <el-col :span="18">
            <single-post :post="post" :loaded="loaded"></single-post>
            <writereply :post="post" :loaded="loaded"></writereply>
            <h3 class="reply-com">{{post.reply}} 评论</h3>
            <replylist :post="post" :loaded="loaded"></replylist>
        </el-col>
    </el-row>
</template>

<script>
    import axios from "axios";
    import SinglePost from "./SinglePost";
    import {Post} from "../../../global/clazz";
    import UserInfo2 from "./UserInfo2";
    import Writereply from "../../reply/writereply";
    import Replylist from "../../reply/replylist";
    export default {
        name: "Article2",
        components: {Replylist, Writereply, UserInfo2, SinglePost},
        provide(){
            return{
                pid: this.$attrs.pid
            }
        },data(){
            return {
                post: new Post(),
                loaded: false
            }
        },
        created() {
                axios.get(`/post/${this.$route.params.pid}`).then(
                    data=>{
                        // console.log(data.data)
                        if(data.data.errno === 0){
                            return data.data;
                        }else{
                            return Promise.reject(data.message);
                        }
                    },error=>Promise.reject(error.toString())
                ).then(
                    data=>{
                        this.loaded = true;
                        this.post = data.extend;
                    },()=>{
                        this.$router.push({name:"notFound"});
                    }
                )
        }
    }
</script>

<style scoped>
    .reply-com{
        border-bottom: 1px solid lightseagreen;
        margin-top: 23px;
        margin-right: 40px
    }
</style>