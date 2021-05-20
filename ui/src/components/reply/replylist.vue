<template>
    <div class="replys">
        <template v-if="!replys.length">
            <el-card class="box-card">
                <div class="no-reply">这竟然没有回复</div>
            </el-card>

        </template>
        <template v-else>
            <el-tabs v-model="activeName">
                <el-tab-pane label="按时间倒序" name="time"></el-tab-pane>
                <el-tab-pane label="按热门排序" name="hot"></el-tab-pane>
            </el-tabs>
        </template>

    </div>

</template>

<script>
    import {Page} from "../../global/clazz";
    import axios from 'axios'
    import {mapState} from "vuex";

    export default {
        name: "replylist",
        props:['post','loaded'],
        data(){
            return {
                replys:[],
                page: new Page(),
                activeName: 'time'
            }
        },
        computed:{
            ...mapState({
                'uid': state=>state.user.uid,
                'isLogin': state=>state.user.isLogin
            })
        },
        watch:{
            loaded(neww){
                if(neww){
                    axios.get(`/reply/post/${this.post.pId}`,{
                        params:{
                            uid: this.uid
                        }
                    }).then(
                        data=>{
                            if(data.data.errno === 0){
                                return data.data;
                            }else{
                                return Promise.reject(data.message);
                            }
                        },error=>Promise.reject(error.toString())
                    ).then(
                        data=>{
                            this.page = data.extend;
                            this.replys = data.extend.list;
                        },error=>{
                            console.log(error);
                        }
                    )
                }
            }
        },
        methods:{
            handleClick(){
                if(this.activeName === 'time'){
                    axios.get
                }
            }
        }
    }
</script>

<style scoped>
    .replys{
        margin-right: 40px;
    }
    >>> .el-tabs__nav-wrap::after{
        background-color: yellow;
    }
</style>