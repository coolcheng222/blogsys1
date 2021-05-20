<template>
    <div class="writing">
        <el-row>
            <el-col :span="5"></el-col>
            <el-col :span="14">
                <forming :put="put"></forming>
            </el-col>
            <el-col :span="5"></el-col>
        </el-row>
    </div>

</template>

<script>
    import Forming from "./forming";
    export default {
        name: "writing",
        components: {Forming},
        data(){
            return{
                tabPosition: 'right'
            }
        },
        mounted() {
            if(!this.$store.state.user.isLogin){
                this.$router.push('/index');
            }
        },
        computed:{
            put(){
                return this.$route.query.put;
            }
        }
    }
</script>

<style scoped>
    .writing{
        background-image: url("~@/assets/writing.png");
        background-size: cover;
        height: 92.5%;
    }
    .el-row{
        height: 92.5%;
    }
</style>