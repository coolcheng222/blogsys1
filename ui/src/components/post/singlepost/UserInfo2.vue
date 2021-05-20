<template>
    <div class="userinfo2">
        <el-header>
            <div class="title">作者<span v-if="myself">(我自己)</span></div>
            <div class="user" @click="$router.push(`/user/${userinfo.uid}`)">{{userinfo.username}}</div>

        </el-header>
        <el-main>
            <info-bar :userinfo="userinfo"></info-bar>
            <div v-if="myself" class="operation">
                    <i class="op el-icon-edit" @click="updateIt"></i>
                    <i class="op el-icon-delete-solid" @click="deleteIt"></i>
            </div>

            <div class="articles">
                <!--eslint-disable-->
                <span>作者的其他文章</span>
                <div class="article" v-for="post1 in posts" @click="$router.push(`/post/${post1.pId}`);$router.go(0)">
                    {{post1.title}}<br>
                    <div class="desp">{{post1.description}}</div>
                </div>
            </div>

        </el-main>
        <el-footer v-if="!myself && userinfo.uid">
            <subbutton :uid="userinfo.uid"></subbutton>
        </el-footer>
    </div>
</template>

<script>
    import {UserInfo} from "../../../global/clazz";
    import axios from 'axios';
    import InfoBar from "../../user/info/InfoBar";
    import Subbutton from "../../sub/subbutton";

    export default {
        name: "UserInfo2",
        components: {Subbutton, InfoBar},
        props:['post','loaded'],
        data(){
            return {
                userinfo: new UserInfo(),
                posts:[]
            }
        },
        mounted() {
            // this.getUserInfo();
        },
        watch: {
            loaded(neww){
              if(neww){
                  this.getUserInfo();
                  this.getPosts();
              }
            }
        },
        methods:{
            getUserInfo(){
                // eslint-disable-next-line no-empty
                axios.get(`userinfo/${this.post.author.uid}`, {}).then(data => {
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
            },
            getPosts(){
                axios.get(`/post/user/${this.post.author.uid}`).then(
                    data=>{
                        if(data.data.errno === 0){
                            this.posts = data.data.extend.list.slice(0,3);
                        }
                    }
                )
            },
            deleteIt(){
                if(confirm("确认删除文章?")){
                    let a = {}
                    Object.assign(a,this.post);
                    a.uid = this.post.author.uid;
                    axios.delete('/post/',{
                        data: a
                    }).then(
                        data=>{
                            if(data.data.errno === 0){
                                alert("删除成功");
                                this.$router.push("/index");
                            }else{
                                alert("删除失败");
                            }
                        }
                    )
                }
            },
            updateIt(){
                this.$store.dispatch('save',this.post);
                this.$router.push('/post/write?put=true');
            }
        },
        computed:{
            myself(){
                return this.$store.state.user.uid === this.userinfo.uid;
            }
        }
    }
</script>

<style scoped>
    .userinfo2{
        margin-top: 30px;
        margin-left: 20px;
        margin-right: 20px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        /*border: 1px solid darkgray;solid*/
    }
    .user{
        text-align: center;
        font-size: 19px;
        font-weight: bold;
        line-height: 40px;
    }
    .user:hover{
        text-decoration: underline;
        cursor: pointer;
    }
    .grid-content{
        text-align: center;
    }
    .title{
        margin-left: 10px;
        padding-top: 10px;
    }
    .el-footer{
        display: flex;
        vertical-align: center;
        justify-content: center;
    }
    .el-button{
        height: 40px;
    }
    .article{
        border-bottom: 1px solid #ada5a5;
        padding-top: 10px;
        padding-left: 20px;
        height: 40px;
        font-size: 19px;

    }
    .desp{
        font-size:12px;
        color: #6f6565;
    }
    .article:hover{
        text-decoration: underline;
        cursor: pointer;
    }
    .articles{
        margin-top: 10px;
    }
    .operation{
        height: 40px;
        display: flex;
        vertical-align: center;
        justify-content: center;
    }
    .operation i{
        margin-top: 15px;
        margin-left: 20px;
        margin-right: 20px;
    }
    .op:hover{
        cursor: pointer;
        color: red;
    }
</style>