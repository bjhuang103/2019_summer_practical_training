<template>
	<view>
		<!-- <view class="topView"> </view> -->
		<view class="header">
			<view class="bg">
				<view class="box">
					<view class="box-hd">
						<view class="avator" @click="setInfo">
							<img v-if="showImg" :src="user.profileUrl" />
						</view>
						<view class="nickname">{{user.username}}</view>
					</view>
					<view class="box-bd">
						<view class="item" @click="goToPage('../tabbar-5-detail/mycomment')">
							<view class="icon"><img src="../../../../static/user/mycomment.png"></view>
							<view class="text">我的评论</view>
						</view>
						<view class="item" @click="goToPage('../tabbar-5-detail/my-release')">
							<view class="icon"><img src="../../../../static/user/myrelease.png"></view>
							<view class="text">我的发布</view>
						</view>
						<view class="item" @click="goToPage('../tabbar-5-detail/my-card')">
							<view class="icon"><img src="../../../../static/user/mycard.png"></view>
							<view class="text">我的帖子</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="list-content" style="margin-top: 130upx;">
			<view class="list">
				<view class="li " @click="goToPage('../tabbar-5-detail/help')">
					<view class="icon">
						<image src="../../static/user/help.png"></image>
					</view>
					<view class="text">帮助中心</view>
					<image class="to" src="../../static/user/to.png"></image>
				</view>
				<view class="li " @click="goToPage('../tabbar-5-detail/about-us')">
					<view class="icon">
						<image src="../../static/user/aboutus.png"></image>
					</view>
					<view class="text">关于我们</view>
					<image class="to" src="../../static/user/to.png"></image>
				</view>
				<view class="li " @click="goToPage('../tabbar-5-detail/feedback')">
					<view class="icon">
						<image src="../../static/user/contactus.png"></image>
					</view>
					<view class="text">意见反馈</view>
					<image class="to" src="../../static/user/to.png"></image>
				</view>
			</view>
			<view class="list">
				<view class="li noborder" @click="logout()">
					<view class="icon">
						<image src="../../static/user/set.png"></image>
					</view>
					<view class="text">退出登录</view>
					<image class="to" src="../../static/user/to.png"></image>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		mounted: function() {
			console.log(this.user);
		},
		data() {
			return {
				avator: '',
				nickname: 'asd',
				//userInfo: this.$store.state.userInfo,
				user: this.$store.state.userInfo,
				showImg: true
			};
		},
		onReady() {
			console.log("ready")
		},
		computed: {
			userInfo() {
				return this.$store.state.userInfo
			}
		},
		watch: {
			userInfo(newValue, oldValue) {
				console.log("show!")
				this.user = newValue
				let savedUrl = this.user.profileUrl
				this.user.profileUrl = ""
				this.showImg = false
				this.$nextTick(function(){
					this.user.profileUrl = savedUrl
					this.showImg = true
				})
			}
		},
		methods: {
			onConfirm(e) {
				this.pickerText = JSON.stringify(e);
				this.detail = e.label;
			},
			goToPage(url) {
				if (!url) return;
				uni.navigateTo({
					url
				});
			},
			setInfo(){
				uni.navigateTo({
					url: '../tabbar-5-detail/update-user?data=' + JSON.stringify(this.user)
				})
			},
			logout() {
				this.$store.commit('logout', '')
				uni.closeSocket();
				uni.reLaunch({
					url: '../login'
				})
				// plus.push.createMessage("youyitiaoxiaoxi!!","type:123123")
				// plus.push.addEventListener("click",(msg) => {
				// 	console.log(msg)
				// })
			}
		},
		onBackPress() {
			if (this.$refs.mpvuePicker.showPicker) {
				this.$refs.mpvuePicker.pickerCancel();
				return true;
			}
			if (this.$refs.mpvueCityPicker.showPicker) {
				this.$refs.mpvueCityPicker.pickerCancel();
				return true;
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #f1f1f1;
		font-size: 30upx;
	}

	.header {
		background: #fff;
		height: 350upx;
		padding-bottom: 110upx;
		.bg {
			width: 100%;
			height: 350upx;
			padding-top: 100upx;
			background-image: url("../../../../static/user/user.jpg");
		}
	}

	.box {
		width: 650upx;
		height: 280upx;
		border-radius: 20upx;
		margin: 0 auto;
		margin-top:70upx;
		background: #fff;
		box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2);

		.box-hd {
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;

			.avator {
				width: 160upx;
				height: 160upx;
				background: #fff;
				border: 5upx solid #fff;
				border-radius: 50%;
				margin-top: -80upx;
				overflow: hidden;

				img {
					width: 100%;
					height: 100%;
				}
			}

			.nickname {
				width: 100%;
				text-align: center;
			}
		}

		.box-bd {
			display: flex;
			flex-wrap: nowrap;
			flex-direction: row;
			justify-content: center;

			.item {
				flex: 1 1 auto;
				display: flex;
				flex-wrap: wrap;
				flex-direction: row;
				justify-content: center;
				border-right: 1px solid #f1f1f1;
				margin: 15upx 0;

				&:last-child {
					border: none;
				}

				.icon {
					width: 60upx;
					height: 60upx;

					img {
						width: 100%;
						height: 100%;
					}
				}

				.text {
					width: 100%;
					text-align: center;
					margin-top: 10upx;
				}
			}
		}
	}

	.list-content {
		background: #fff;
	}

	.list {
		width: 100%;
		border-bottom: 15upx solid #f1f1f1;
		background: #fff;

		&:last-child {
			border: none;
		}

		.li {
			width: 92%;
			height: 100upx;
			padding: 0 4%;
			border-bottom: 1px solid rgb(243, 243, 243);
			display: flex;
			align-items: center;

			&.noborder {
				border-bottom: 0
			}

			.icon {
				flex-shrink: 0;
				width: 50upx;
				height: 50upx;

				image {
					width: 50upx;
					height: 50upx;
				}
			}

			.text {
				padding-left: 20upx;
				width: 100%;
				color: #666;
			}

			.to {
				flex-shrink: 0;
				width: 40upx;
				height: 40upx;
			}
		}
	}

	.topView {
		width: 100%;
		height: var(--status-bar-height);
		background-color: #4191ea;
	}
</style>
