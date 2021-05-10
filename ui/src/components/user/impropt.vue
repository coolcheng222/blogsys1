<template>
    <el-dialog
            v-model="visible"
            width="40%"
            :close-on-click-modal="false"
            :show-close="false"
            :close-on-press-escape="false"
            >
        <div v-if="completed">
            {{message}}
        </div>
        <div v-else>
            <el-progress :percentage="progress"></el-progress>
        </div>
        <template #footer>
             <span class="dialog-footer">
               <el-button type="primary" :disabled="!completed" @click="$emit('finish')">确 定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
    export default {
        name: "impropt",
        props:[
            'dialogVisible',
            'completed',
            'progress',
            'message',
            'success'
        ],computed:{
            visible(){
                return this.dialogVisible;
            }
        }

    }
</script>

<style scoped>
    .el-dialog div{
        font-size: 18px;
        text-align: center;
    }
</style>