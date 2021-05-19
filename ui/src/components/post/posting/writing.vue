<template>
    <div class="writing">
        <el-row>
            <el-col :span="6"></el-col>
            <el-col :span="12">
                <forming></forming>
            </el-col>
            <el-col :span="6"></el-col>
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
        }
    }
</script>

<style scoped>
    .writing{
        background-image: url("~@/assets/writing.png");
        background-size: cover;
        height: 91.5%;
    }
    .el-row{
        height: 91.5%;
    }
</style>