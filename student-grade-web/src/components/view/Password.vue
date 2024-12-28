<script setup>
import { ref } from "vue"
import { Lock } from "@element-plus/icons-vue"
import { ElMessage } from "element-plus";
import {updateStudentPasswordServer} from "@/api/student"
import {updateAdminPasswordServer} from "@/api/admin"
import userInfoServer from "@/store/useInfo"
const password = ref({
    oldPwd: '',
    newPwd: '',
    rePwd: ''
})
const form=ref(null);

const updatePassword = () => {
    form.value.validate( ( async valid=>{
        if(valid){
            //如果是学生
            if(userInfoServer().userInfo.role===1){
                await updateStudentPasswordServer(password);
            }
            //如果是管理员
            else if(userInfoServer().userInfo.role===0){
                await updateAdminPasswordServer(password);
            }
            ElMessage.success("修改密码成功！")
            //清空
            password.value.oldPwd='';
            password.value.newPwd='';
            password.value.rePwd='';
        }else{
            ElMessage.error("数据格式错误")
        }
    }))

    
}

const checkPassword=(rule,value,callback)=>{
    if(value!==password.value.newPwd){
        callback(new Error('前后密码不一致'))
    }else{
        callback();
    }
}

const rules = {
    oldPwd: [
        { required: true, message: "请输入原密码", trigger: 'blur' }
    ],
    newPwd: [
        { required: true, message: "请输入新密码", trigger: 'blur' },
        { pattern: /^(\d){6,}$/, message: '密码长度最少在6位', trigger: 'blur' }
    ],
    rePwd: [
        { required: true, message: "请输入新密码", trigger: 'blur' },
        { validator: checkPassword, trigger: 'blur' }
    ]
}

</script>

<template>
    <div>
        <el-form :model="password" :rules="rules" ref="form">
            <el-form-item prop="oldPwd" label="原来密码">
                <el-input :prefix-icon="Lock" type="password" v-model="password.oldPwd" placeholder="输入原密码" />
            </el-form-item>

            <el-form-item prop="newPwd" label="新设密码">
                <el-input :prefix-icon="Lock" type="password" v-model="password.newPwd" placeholder="输入新密码" />
            </el-form-item>

            <el-form-item prop="rePwd" label="重复密码">
                <el-input :prefix-icon="Lock" type="password" v-model="password.rePwd" placeholder="输入新密码" />
            </el-form-item>
        </el-form>
        <el-form-item>
            <el-button type="primary" @click="updatePassword()">确认修改</el-button>
        </el-form-item>
    </div>
</template>

<style></style>