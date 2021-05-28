<template>
<!--    eslint-disable-->
    <el-row>
        <el-col :span="1"><div class="desc" >收藏多</div></el-col>
        <el-col :span="11"><order-hot-list :order-clause="clauses[0]"></order-hot-list></el-col>
        <el-col :span="11"> <order-hot-list :order-clause="clauses[1]"></order-hot-list></el-col>
        <el-col :span="1"><div class="desc">评论多</div></el-col>
    </el-row>


</template>

<script>
    import OrderHotList from "./bloghot/OrderHotList";
    export default {
        name: "indexBody2",
        components: {OrderHotList},

        data(){
            return {
                clauses: ['faved','reply']
            }
        }
    }
</script>

<style scoped>
    .desc{
        position: fixed;
        width: 10px;
        text-align: center;
        font-size: 20px;
        margin-left: 20px;
        margin-right: 20px;
        margin-top: 20px;
        word-wrap: break-word; word-break: normal;
    }
</style>