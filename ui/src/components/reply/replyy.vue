<template>
    <div class="replying">
        <div class="author" @click="$router.push(`/user/${item.user.uid}`)">{{item.user.username}} :</div>
        <div class="content">{{item.content}}</div>
        <div class="footer">
            <span class="time">{{getTime(item.time)}} </span>&nbsp;&nbsp;&nbsp;
            <span class="star"><i class="el-icon-s-opportunity" @click="toggleStar" :class="{stared:(tempstar)?!item.stared:item.stared}"/> {{item.stars + staring}}</span>&nbsp;&nbsp;&nbsp;
            <span class="delete" @click="deleting" v-if="myself"><i class="el-icon-delete"></i></span>
        </div>
    </div>
</template>

<script>
    import {dateFormat, Stared} from "../../global/clazz";
    import {mapState} from "vuex";
    import axios from 'axios';

    export default {
        name: "replyy",
        props:['item'],
        methods:{
            getTime(date) {
                return dateFormat("YYYY-mm-dd HH:MM", date);
            },
            toggleStar: function () {
                if(!this.isLogin){
                    this.$store.dispatch('toLogin',this.$route.fullPath);
                    this.$router.push('/login');
                }
                this.tempstar = !this.tempstar;
                let stared = new Stared();
                stared.pid = this.item.post.pId;
                stared.uid = this.uid;
                stared.reid = this.item.reid;
                if (this.item.stared && this.tempstar) {
                    axios.delete('/star',{
                        data:
                            stared
                    }).then(
                        data=>{
                            // eslint-disable-next-line no-empty
                            if(data.data.errno === 0){

                            }else{
                                this.tempstar = !this.tempstar;
                            }
                        },
                        ()=>{
                            this.tempstar = !this.tempstar;
                        }
                    )
                }
                else{
                    axios.post('/star',stared).then(
                        data=>{
                            // eslint-disable-next-line no-empty
                            if(data.data.errno === 0){

                            }else{
                                this.tempstar = !this.tempstar;
                            }
                        },
                        ()=>{
                            this.tempstar = !this.tempstar;
                        }
                    )
                }
            },
            deleting: function(){
                if(confirm("确定删除?")){
                    axios.delete('reply',{
                        params:{
                            uid: this.item.user.uid,
                            pid: this.item.post.pId,
                            reid: this.item.reid
                        }
                    }).then(
                        data=>{
                            if(data.data.errno === 0){
                                this.$router.go(0);
                            }else{
                                alert('删除失败');
                            }
                        },error=>{
                            alert('服务器错误' + error);
                        }
                    )
                }
            }
        },
        data(){
            return {
                tempstar: false
            }
        },
        computed:{
            myself(){
                return this.uid === this.item.user.uid;
            },
            ...mapState({
                'uid': state=>state.user.uid,
                'isLogin': state=>state.user.isLogin
            }),
            staring(){
                if(this.item.stared){
                    return this.item.stared && (!this.tempstar) ? 0: -1;
                }else{
                    return !this.item.stared && (!this.tempstar) ? 0: 1;
                }

            }
        }
    }
</script>

<style scoped>
    .replying{
        height: 78px;
        border-bottom: 1px solid red;
        margin-left: 20px;
        margin-right: 20px;
    }
    .author{
        margin-top: 10px;
        font-size: 16px;
        font-weight: bold;
        color: #6D757A;
        margin-left: 5px;
        margin-bottom: 5px;
    }
    .author:hover{
        cursor: pointer;
        color: cornflowerblue;
    }
    .content{
        margin-left: 5px;
        margin-top:10px;
        margin-bottom: 10px;
        word-break: break-all;
        word-wrap: break-word;
        font-size: 18px;
    }
    .time{
        margin-left: 5px;
        font-size: 14px;
        color: #ada5a5;
    }
    .stared{
        color: indianred;
    }
    .el-icon-s-opportunity:hover{
        color: #cb0eff;
        cursor: pointer;
    }
    .delete:hover{
        cursor: pointer;
        color: red;
    }
</style>