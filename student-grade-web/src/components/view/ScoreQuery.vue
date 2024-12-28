<script setup>
import { ref } from "vue"
import { getStudentCourseListPersonalServer,feedbackServer } from "@/api/studentCourse"
import { ElMessage, ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue'

const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)

const handleCurrentChange = async (number) => {
  console.log("current page: ", number)
  searchBody.value.pageNum = number
  await searchButton()
}
const handleSizeChange = async (number) => {
  console.log(" items per page", number)
  searchBody.value.pageSize = number;
  await searchButton()
}

const tableData = ref([
  {
    courseNo: '1001',
    courseName: '高等数学'
  },
  {
    courseNo: '1001',
    courseName: '高等数学'
  },
  {
    courseNo: '1001',
    courseName: '高等数学'
  }
])

const searchBody = ref({
  courseNo: '',
  pageNum: 1,
  pageSize: ''
})

const searchButton = async () => {
  const result = await getStudentCourseListPersonalServer(searchBody);
  tableData.value = result.data.items;
  allNumber.value = result.data.total;
}
searchButton()

const gradeBody=ref({
  courseNo:'',
  studentId:'',
  feedback:''
})

const form=ref(null)
const centerDialogVisible=ref(false)
const reset = () => {
  searchBody.value = {}
}
const rules={
  feedback:[
    {required:true,message:'反馈内容不能为空',trigger:'blur'}
  ]
}
const feedback = (row) => {
  gradeBody.value=JSON.parse( JSON.stringify(row));
  centerDialogVisible.value=true;
}



const cancel=()=>{
  centerDialogVisible.value=false;
  gradeBody.value={}
  form.value.resetFields()
}


const feedbackButton=()=>{
  form.value.validate((async valid=>{
    if(valid){
      await feedbackServer(gradeBody);
      ElMessage.success('反馈成功')
      centerDialogVisible.value=false;
      await searchButton();
    }else{
      ElMessage.error('反馈内容有误')
    }
  }))
}


</script>

<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input v-model="searchBody.courseNo" style="width: 220px;" placeholder="课程编号" :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column type="index" width="60px" label="序号" />
        <el-table-column prop="courseNo" label="课程编号" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="score" label="课程成绩" />
        <el-table-column prop="comment" label="老师评语" />
        <el-table-column prop="feedback" label="我的反馈" />
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button v-if="row.feedback==null||row.feedback==''" type="danger" size="small" @click="feedback(row)">
              反馈
            </el-button>
            <el-button v-else  size="small" disabled="true">
              已反馈
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <!--分页插件-->
      <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
        :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes" :total="allNumber"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 评估弹窗 -->
    <el-dialog v-model="centerDialogVisible" title="反馈" width="350" center>
      <el-form :model="gradeBody" :rules="rules" ref="form">
        <el-form-item label="反馈" prop="feedback">
          <el-input type="textarea" v-model="gradeBody.feedback" ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel()">取消</el-button>
          <el-button type="primary" @click="feedbackButton()">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped></style>