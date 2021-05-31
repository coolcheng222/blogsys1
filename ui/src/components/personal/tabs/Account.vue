<template>
    <el-row>
        <el-col :span="4"></el-col>
        <el-col :span="16">
            <div class="account">
                <div class="item">修改密码</div>
                <input v-model="pass"  type="password" placeholder="直接输入新密码就行"/>
                <button @click="change">改</button>
<!--                <div class="item">修改邮箱</div>-->
            </div>
        </el-col>
        <el-col :span="4"></el-col>
    </el-row>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "Account",
        data(){
            return {
                pass:''
            }
        },
        computed:{
            uid(){
                return this.$route.params.uid;
            }
        },
        methods:{
            change(){
                axios.put(`user`,{
                    uid:this.uid,
                    password:this.pass
                }).then(
                    data=>{
                        if(data.data.errno === 0){
                            alert("成功");
                        }else{
                            alert("失败");
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>
    .account{
        margin: 10px auto;
        width: 100%;
        color:blue;
        text-decoration: underline;
    }
</style>