<template>
  <div class="form-container">
    <el-form
      :model="missing_person"
      :rules="rules"
      ref="ruleForm"
      label-width="150px"
      class="demo-ruleForm"
    >
      <div style="padding-top:10px">
        <el-divider content-position="left">
          <span class="font-size-1-3em">失踪者信息</span>
        </el-divider>
      </div>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="missing_person.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="missing_person.sex">
          <el-radio label="男"></el-radio>
          <el-radio label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出生日期" required>
        <el-form-item prop="birthday" class="fill-width">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="missing_person.birthday"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
      </el-form-item>
      <el-form-item label="身高(cm)" prop="height">
        <el-slider v-model="missing_person.height" :min="100" :max="250"></el-slider>
      </el-form-item>
      <el-form-item label="失踪人籍贯" prop="native_location">
        <el-input v-model="missing_person.nativePlace"></el-input>
      </el-form-item>
      <el-form-item label="失踪日期" required>
        <el-form-item prop="date">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="missing_person.date"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
      </el-form-item>
      <el-form-item label="失踪时地址" prop="place">
        <el-input v-model="missing_person.place"></el-input>
      </el-form-item>
      <el-form-item label="失踪人特征描述" prop>
        <el-input type="textarea" v-model="missing_person.babyDescription"></el-input>
      </el-form-item>
      <el-form-item label="失踪经过" prop>
        <el-input type="textarea" v-model="missing_person.missDescription"></el-input>
      </el-form-item>
      <el-form-item label="其余信息" prop>
        <el-input type="textarea" v-model="missing_person.otherDescription"></el-input>
      </el-form-item>
      <el-form-item label="其余说明" prop>
        <el-input type="textarea" v-model="missing_person.otherExplain"></el-input>
      </el-form-item>

      <div style="padding-top:20px ">
        <el-divider content-position="left"><span class="font-size-1-3em">联系人信息</span></el-divider>
      </div>
      <el-form-item label="联系人姓名" prop>
        <el-input v-model="missing_person.contactName"></el-input>
      </el-form-item>
      <el-form-item label="联系人与失踪者关系" prop>
        <el-input v-model="missing_person.contactRel"></el-input>
      </el-form-item>
      <el-form-item label="联系人地址" prop>
        <el-input v-model="missing_person.contactAddress"></el-input>
      </el-form-item>
      <el-form-item label="联系人邮箱地址" prop="contactEmail">
        <el-input v-model="missing_person.contactEmail"></el-input>
      </el-form-item>
      <el-form-item label="联系人电话号码" prop="contactPhone">
        <el-input v-model="missing_person.contactPhone"></el-input>
      </el-form-item>
      <el-form-item label="联系人其它联系方式" prop>
        <el-input type="textarea" v-model="missing_person.otherContactMethod"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="fillLoading">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { request, fetch } from "@/api/api";
import axios from "axios";
import URLS from "@/config/config";
export default {
  name: "FillRegistInfo",
  props: {
    fillType: Number
  },
  data() {
    return {
      missing_person: {
        // 失踪人信息
        name: "",
        sex: "男",
        height: 0,
        birthday: "",
        place: "",
        nativePlace: "",
        date: "",
        babyDescription: "",
        missDescription: "",
        backGround: "",
        otherDescription: "",
        otherExplain: "",
        // 联系人信息
        contactName: "",
        contactRel: "",
        contactAddress: "",
        contactEmail: "",
        contactPhone: "",
        otherContactMethod: "",
        // 外键
        user: {
          id: this.$store.state.userID
        }
      },
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        birthday: [
          {
            type: "date",
            required: true,
            message: "请选择出生日期",
            trigger: "blur"
          }
        ],
        height: [{ required: true, message: "请选择身高", trigger: "blur" }],
        date: [
          {
            type: "date",
            required: true,
            message: "请选择失踪时间",
            trigger: "blur"
          }
        ],
        place: [{ required: true, message: "请输入失踪地址", trigger: "blur" }],
        contactEmail: [{ type: "email" }],
        contactPhone: [
          { required: true, message: "请输入联系人电话", trigger: "blur" }
        ]
      },
      fillLoading: false
    };
  },
  methods: {
    submitForm(formName) {
      this.fillLoading = true;
      this.$refs[formName].validate(valid => {
        if (valid) {
          // alert("submit!");
          console.log(this.missing_person)
          let url =
            this.fillType == 1
              ? URLS.lostBabyInsertUrl
              : URLS.matchBabyInsertUrl;
          axios
            .post(url, this.missing_person)
            .then(data => {
              if (data.data.rtnCode == 200) {
                console.log("success", data);
                this.$store.commit("setImageId", data.data.data.id);
                this.$emit("on-next-step-click");
              }
              this.fillLoading = false;
            })
            .catch(error => {
              console.log(error);
              this.fillLoading = false;
            });
        } else {
          console.log("error submit!!");
          this.fillLoading = false;
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped>
.form-container {
  width: 50%;
  left: 25%;
  position: relative;
  margin-top: 20px;
}
</style>
