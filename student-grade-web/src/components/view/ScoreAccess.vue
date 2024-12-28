<script setup>
import { ref } from "vue";
import { getStudentCourseListServer, accessServer } from "@/api/studentCourse.js"
import { Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';

const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)

const searchBody = ref({
  courseNo: '',
  studentName: '',
  pageNum: 1,
  pageSize: 3
})

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
    courseNo: '1234',
    courseName: '高等数学',
    studentNo: '110',
    studentName: '张三'
  },
  {
    courseNo: '1234',
    courseName: '高等数学',
    studentNo: '110',
    studentName: '张三'
  },
  {
    courseNo: '1234',
    courseName: '高等数学',
    studentNo: '110',
    studentName: '张三'
  },
])

const searchButton = async () => {
  const result = await getStudentCourseListServer(searchBody);
  tableData.value = result.data.items;
  allNumber.value = result.data.total;
}
searchButton()

const reset = () => {
  searchBody.value = {}
}

const gradeBody = ref({
  courseNo: '',
  studentId: '',
  score: '',
  comment: '',
  feedback: '',
  tempScore: '',//临时成绩，用于窗口的Input禁用与不禁用的输入，有成绩就禁输入
})

//评估bool
const centerDialogVisible = ref(false)

//定义检验表达式
const rules = {
  score: [
    { required: true, message: '请输入成绩', trigger: 'blur' },
    { pattern: /^\d{1,2}(\.\d?)?$/, message: '格式错误', trigger: 'blur' }
  ],
  tempScore: [
    { required: true, message: '请输入成绩', trigger: 'blur' },
    { pattern: /^\d{1,2}(\.\d?)?$/, message: '格式错误', trigger: 'blur' }
  ],
}

const access = (row) => {
  gradeBody.value = JSON.parse(JSON.stringify(row))//深拷贝
  gradeBody.value.tempScore=gradeBody.value.score;
  centerDialogVisible.value = true;
}


const form = ref(null)
const accessButton = async () => {
  // if(!form){
  //   ElMessage.message("数据格式错误！");
  //   return ;
  // }
  gradeBody.value.score = gradeBody.value.tempScore;
  // console.log("gradeBody.value=",gradeBody.value);

  form.value.validate((async valid => {
    if (valid) {
      console.log("1111111");
      await accessServer(gradeBody);
      await searchButton();
      ElMessage.success('评估成功');
      gradeBody.value = {}
      centerDialogVisible.value = false
    }
  }))
  // console.log("gradeBody.value=", gradeBody.value);
}
const cancel = () => {
  centerDialogVisible.value = false
  form.value.resetFields()
}

const detailDialog=ref(false);
const detail = (row) => {
  gradeBody.value = JSON.parse(JSON.stringify(row))//深拷贝
  console.log("gradeBody.value=",gradeBody.value);
  detailDialog.value=true;
}
</script>

<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-input v-model="searchBody.courseNo" style="width: 220px;" placeholder="课程编号" :prefix-icon="Search" />
      <el-input v-model="searchBody.studentName" style="width: 220px;" placeholder="学生姓名" :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column type="index" width="60px" label="序号" />
        <el-table-column prop="courseNo" label="课程编号" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="studentId" label="学生编号" />
        <el-table-column prop="studentName" label="学生名称" />
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button v-if="row.score==null" type="primary" size="small" @click="access(row)">
              评估
            </el-button>
            <el-button v-else  size="small" @click="access(row)" disabled="true">
              已评估
            </el-button>
            <el-button type="info" size="small" @click="detail(row)">
              详情
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
    <el-dialog v-model="centerDialogVisible" title="评估成绩" width="350" center>
      <el-form :model="gradeBody" :rules="rules" ref="form">

        <el-form-item v-if="gradeBody.score == null || gradeBody.score == ''" label="课程成绩" prop="tempScore">
          <el-input v-model="gradeBody.tempScore" />
        </el-form-item>

        <el-form-item v-else label="课程成绩" prop="score">
          <el-input v-model="gradeBody.score" disabled="true" />
        </el-form-item>

        <el-form-item label="成绩评语">
          <el-input type="textarea" v-model="gradeBody.comment" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel()">取消</el-button>
          <el-button type="primary" @click="accessButton()">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 评估弹窗 -->
    <el-dialog v-model="detailDialog" title="详情成绩" width="350" center>
      <el-row>
        <span>课程编号：</span>
        <el-text class="mx-1">{{ gradeBody.courseNo }}</el-text>
      </el-row>
      <el-row>
        <span>课程名称：</span>
        <el-text class="mx-1">{{ gradeBody.courseName }}</el-text>
      </el-row>
      <el-row>
        <span>学生学号：</span>
        <el-text class="mx-1">{{ gradeBody.studentId }}</el-text>
      </el-row>
      <el-row>
        <span>学生姓名：</span>
        <el-text class="mx-1">{{ gradeBody.studentName }}</el-text>
      </el-row>
      <el-row>
        <span>课程成绩：</span>
        <el-text class="mx-1">{{ gradeBody.score }}</el-text>
      </el-row>
      <el-row>
        <span>课程评语：</span>
        <el-text class="mx-1">{{ gradeBody.comment }}</el-text>
      </el-row>
      <el-row>
        <span>学生反馈：</span>
        <el-text class="mx-1">{{ gradeBody.feedback?gradeBody.feedback:"暂无反馈" }}</el-text>
      </el-row>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="detailDialog=false">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped></style>