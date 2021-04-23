<template>
    <div class="kaptcha" @click="change"><img :src="url"></div>
</template>

<script>
    import axios from '@/global/axiosConfig.js';
    export default {

        name: "kaptcha",
        data(){
            return {
                url: axios.defaults.baseURL + "kaptcha"
            }
        },methods:{
            change(){
                this.url = axios.defaults.baseURL + "kaptcha?i=" + Math.random();
            }
        }
    }
</script>

<style scoped>
    .kaptcha{
        margin-left: 6px;
    }
</style>