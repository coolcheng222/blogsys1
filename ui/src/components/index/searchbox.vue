<template>
    <div class="wrap">
        <el-popover
                placement="top-start"
                title="历史记录"
                :width="200"
                trigger="focus"
                :auto-close="10000"
        >
            <template v-if="history.length">
                <!--eslint-disable-->
                <div class="history" @click="change(his)" v-for="his in history">{{his}}</div>
            </template>
            <template v-else>
                <div class="history">没有历史记录</div>
            </template>
            <template #reference>
                <el-input @focus="fetchHistory" size="large" v-model="search" placeholder="只能搜索博客"></el-input>
            </template>
        </el-popover>

        <el-button @click="searching" type="primary" round><i class="el-icon-search"></i></el-button>
    </div>
</template>

<script>
    import axios from 'axios'
    import qs from 'qs'
    export default {
        name: "searchbox",
        data(){
            return{
                search: '',
                history:[]
            }
        },
        watch: {
            $route(to,from){
                console.log(from);
                if(/\/search/.test(from.fullPath) && /\/search/.test(to.fullPath)){
                    this.$router.go(0);
                }
            }
        },
        methods:{
            fetchHistory(){
                if(!this.$store.state.user.isLogin){
                    return;
                }else{
                    axios.get(`/history/search?uid=${this.$store.state.user.uid}`).then(
                        data=>{
                            if(data.data.errno === 0){
                                this.history = data.data.extend;
                            }
                        }
                    )
                }
            },
            searching(){
                axios.post('search',qs.stringify({title: this.search}));
                this.$router.push(`/search/${this.search}`);
            },
            change(his){
                this.search = his;
            }
        }
    }
</script>

<style scoped>

    .wrap {
        background: rgba(255, 255, 255, 0.4);
        position: absolute;
        top: 50%;
        left: 64%;
        transform: translate(-50%, -50%);
        -webkit-transform: translate(-50%, -50%);
        -moz-transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        -o-transform: translate(-50%, -50%);
        width: 420px;
        height: 44px;
        border-radius: 24px;
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
    .el-button {
        margin: 8px 0;
        float: right;
        position: absolute;
        top: -12%;
        left: 84%;
    }
    .history{
        padding-left: 15px;
    }
    .history:hover{
        cursor: pointer;
        background-color: #E7E7E7;
    }
</style>