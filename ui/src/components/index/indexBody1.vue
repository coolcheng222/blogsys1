<template>
    <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="17"><blog-list></blog-list></el-col>
        <el-col :span="6">a</el-col>
    </el-row>
</template>

<script>
    import BlogList from "./blogList";
    export default {
        name: "indexBody1",
        components: {BlogList}
    }
</script>

<style scoped>
    .el-row{
        background-color: #F6F6F6;
    }
</style>