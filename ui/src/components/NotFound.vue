<template>
    <index-header></index-header>
    <el-empty description="404找不着"></el-empty>
</template>

<script>
    import IndexHeader from "./index/indexHeader";
    export default {
        name: "NotFound",
        components: {IndexHeader}
    }
</script>

<style scoped>

</style>