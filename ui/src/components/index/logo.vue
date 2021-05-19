<template>
    <li class="imgimg" @click="$emit('toggle')">
        <router-link to="/index" :class="'aaa'"><img style="height: 60px" src="@/assets/logo2.png">
        </router-link>
        <i v-if="!showing" class="el-icon-arrow-down"></i>
        <i v-else class="el-icon-arrow-right"></i>
    </li>
</template>

<script>
    export default {
        name: "logo",
        props:[
            'showing'
        ],
        emits:['toggle']
    }
</script>

<style scoped>
    img{
        height: 100%;
        position: absolute;
        top:-8%;
        left:2%;
    }
    .imgimg i{
        position: absolute;
        top: 45%;
        left: 10%
    }
</style>