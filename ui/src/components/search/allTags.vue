<template>
    <div class="userinfo">
        <div class="header"><i class="el-icon-price-tag"></i> 热门标签</div>
        <el-main>
            <ul>
                <template v-if="!loaded">
                    <!--eslint-disable-next-line-->
                    <li v-for="i in [1,2,3,4,5]">
                    </li>
                </template>
                <template v-else>
                    <!--eslint-disable-->
                    <li v-for="tag in tags">
                        <div class="atag">
                            <div class="tag1" @click="$router.push(`/tag/${tag.tag}`)">{{tag.tagname}} </div>
                            <div class="posts">({{tag.posts}})</div>
                        </div>

                    </li>
                    <!--eslint-disable-next-line-->
                </template>

            </ul>
        </el-main>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: "allTags",
        data(){
            return {
                loaded: false,
                tags: []
            }
        },
        mounted() {
            axios.get('tag/order').then(
                data=>{
                    if(data.data.errno === 0){
                        this.tags = data.data.extend;
                        this.tags = this.tags.slice(0,5)
                        this.loaded = true;
                    }
                }
            )
        }
    }
</script>

<style scoped>
    .userinfo {
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        margin-right: 20px;
        margin-top: 10px;
        background-color: #fff;
    }

    .header {
        margin-top: 10px;
        margin-left: 10px;
        line-height: 30px;
        height: 30px;
    }

    li {
        /*text-decoration: underline;*/
        /*width: 400px;*/
        width: 175px;
        /*border-top: 1px solid #e5d7d7;*/
        list-style-type: none;
    }

    .userinfo {
        position: fixed;
    }
    .tag1{
        margin-top: 6px;
        margin-bottom: 6px;
        display: inline;
    }
    .posts{
        flex: 0 0 58%;
        display: inline;
        position: absolute;
        right: 30px;
        /*margin-top: 6px;*/
    }
    .atag{
        margin-top: 10px;
        margin-bottom: 10px;
        border-bottom: 1px solid rgba(255, 234, 255, 0.8);
    }
    .tag1:hover{
        color: #2d2c68;
        text-decoration: underline;
        cursor: pointer;
    }
</style>