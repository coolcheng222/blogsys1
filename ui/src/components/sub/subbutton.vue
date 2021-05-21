<template>
    <el-button v-if="loading" disabled><i class="el-icon-loading"></i></el-button>
    <el-button @click="subscribe" v-else-if="!sub">关注</el-button>
    <el-button @click="unsubscribe" v-else type="primary" plain>取消关注</el-button>
</template>

<script>
    import axios from 'axios'
    import {mapState} from "vuex";
    import qs from 'qs';
    export default {
        name: "subbutton",
        props:['uid'],
        data(){
            return {
                sub: false,
                loading: false
            }
        },
        mounted() {
            this.getSub();
        },
        methods:{
            getSub(){
                axios.get(`/sub/check?subber=${this.$store.state.user.uid}&subbed=${this.uid}`)
                    .then(
                        data=>{
                            if(data.data.errno === 0){
                                this.sub = true;
                            }
                        }
                    )
            },
            subscribe(){

                if(!this.$store.state.user.isLogin){
                    this.$store.dispatch('toLogin',this.$route.fullPath);
                    this.$router.push('/login');
                }else{
                    this.loading = true;
                    axios.post('/sub',qs.stringify({
                        subber:this.myuid,
                        subbed:this.uid
                    })).then(
                        data=>{
                            if(data.data.errno === 0){
                                this.sub = true;
                                this.loading = false;
                            }
                        }
                    )
                }
            },
            unsubscribe(){
                if(!this.$store.state.user.isLogin){
                    this.$store.dispatch('toLogin',this.$route.fullPath);
                    this.$router.push('/login');
                }else{
                    this.loading = true;
                    axios.delete(`/sub?subber=${this.myuid}&subbed=${this.uid}`).then(
                        data=>{
                            if(data.data.errno === 0){this.loading = false;
                                this.sub = false;
                            }
                        }
                    )
                }
            }
        },computed:{
            ...mapState({
                'myuid': state=>state.user.uid
            })
        }
    }
</script>

<style scoped>

</style>