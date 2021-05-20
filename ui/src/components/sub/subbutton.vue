<template>
    <el-button @click="subscribe" v-if="!sub">关注</el-button>
    <el-button @click="unsubscribe" v-else type="primary" plain>取消关注</el-button>
</template>

<script>
    import axios from 'axios'
    import {mapState} from "vuex";
    export default {
        name: "subbutton",
        props:['uid'],
        data(){
            return {
                sub: false
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
                    axios.post('/sub',{
                        subber:this.myuid,
                        subbed:this.uid
                    }).then(
                        data=>{
                            if(data.data.errno === 0){
                                this.sub = true;
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
                    axios.delete(`/sub?subber=${this.myuid}&subbed=${this.uid}`).then(
                        data=>{
                            if(data.data.errno === 0){
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