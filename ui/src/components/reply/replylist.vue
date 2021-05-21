<template>
    <div class="replys">
        <template v-if="loaded2">
            <template v-if="!replys.length">
                <el-card class="box-card">
                    <div class="no-reply">这竟然没有回复</div>
                </el-card>

            </template>
            <template v-else>
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane clause="time desc" label="按时间倒序" name="time"></el-tab-pane>
                    <el-tab-pane clause="stars desc" label="按热门排序" name="hot"></el-tab-pane>
                </el-tabs>
                <!--eslint-disable-->
                <replyy v-for="item in replys" :item="item"></replyy>
                <div class="page">


                <el-pagination
                        background
                        :current-page="page.pageNum"
                        layout="prev, pager, next"
                        :page-count="page.pages"
                        @current-change="handlePage">
                </el-pagination>
                </div>
            </template>
        </template>
        <template v-else>
            <el-skeleton></el-skeleton>
        </template>


    </div>

</template>

<script>
    import {Page} from "../../global/clazz";
    import axios, {CancelToken} from 'axios'
    import {mapState} from "vuex";
    import Replyy from "./replyy";

    // eslint-disable-next-line no-unused-vars
    var c;
    export default {
        name: "replylist",
        components: {Replyy},
        props:['post','loaded'],
        data(){
            return {
                replys:[],
                page: new Page(),
                activeName: 'time',
                loaded2: false,
                clauses: {
                    time:'time desc',
                    hot:'stars desc'
                },
                clause: 'time desc'
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
                    ).finally(()=>{
                        this.loaded2 = true;
                    })

                }
            }
        },
        methods:{
            handleClick(tab){
                // eslint-disable-next-line no-empty
                if(this.clauses[tab.props.name] === this.clause){
                    return;
                }else{
                    this.clause = this.clauses[tab.props.name];
                    this.getReply(this.clauses[tab.props.name],1);
                }
            },
            handlePage(paging){
                this.getReply(this.clause,paging);
            },
            getReply(clause,paging){
                if(c && c instanceof Function){
                    c();
                }
                if(!paging){
                    paging = 1;
                }
                this.loaded2 = false;
                axios.get(`/reply/post/${this.post.pId}`,{
                    params:{
                        uid: this.uid,
                        clause: clause,
                        page: paging
                    },
                    cancelToken: new CancelToken(function executor(cancel) {
                    // An executor function receives a cancel function as a parameter
                        c = cancel;

                    })
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
                ).finally(()=>{
                    this.loaded2 = true;
                })
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
    >>> .el-tabs__item{
        font-weight: bold;
    }
    .box-card{
        background-color: rgba(255,255,255,1);
    }
    .page{
        margin-top: 10px;
        display: flex;
        justify-content: center;
        margin-bottom: 10px;
    }
</style>