package com.example.progressanimate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.View
import kotlin.math.atan2


class ProgressView(context: Context): View(context) {
    private var mMaxProgress: Float = 0f  //最大进度值
    private var mCurrentProgress: Float = 0f //当前进度
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path
    private lateinit var mPathMeasure: PathMeasure
    private var width: Int = 0
    private var height: Int = 0
    private var mPathOffset: Float = 0f //水波偏移
    private var perCycleWidth: Float = 0f //一个周期宽
    private var perCycleLength: Float = 0f //一个周期路径长度
    private var cycleCount: Float = 6f //周期个数
    private lateinit var mBoat: Bitmap
    private var pos = FloatArray(2)
    private var tan = FloatArray(2)

    init {
        init()
    }

    private fun init() {
        // 进行数据初始化
        mMaxProgress = 100f
        mCurrentProgress = 50f
        mPaint = Paint().apply {
            strokeWidth = 4f
            color = 0x771234FF
            textSize = 40f
        }
        mPath = Path()
        mPathMeasure = PathMeasure()
        mBoat = BitmapFactory.decodeResource(resources, R.mipmap.boat)
        pos = FloatArray(2)
        tan = FloatArray(2)
    }
    fun getMaxProgress(): Float {
        return mMaxProgress
    }

    fun setMaxProgress(mMaxProgress: Float) {
        this.mMaxProgress = mMaxProgress
    }

    fun getCurrentProgress(): Float {
        return mCurrentProgress
    }
    fun setCurrentProgress(mCurrentProgress: Float) {
        if (mCurrentProgress >= mMaxProgress) {
            this.mCurrentProgress = mMaxProgress
        } else if (mCurrentProgress <= 0) {
            this.mCurrentProgress = 0f
        } else this.mCurrentProgress = mCurrentProgress
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec);
        height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec);
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //对path进行初始化
        mPath.reset()
        //获取周期宽
        perCycleWidth = w / cycleCount
        //path绘制一段正弦曲线
        mPath.moveTo(0f, (h / 2).toFloat())
        var i = 0
        while (i < cycleCount + 2) {
            mPath.rQuadTo(perCycleWidth / 4, -perCycleWidth / 4, perCycleWidth / 2, 0f)
            mPath.rQuadTo(perCycleWidth / 4, perCycleWidth / 4, perCycleWidth / 2, 0f)
            i++
        }
        //pathMeasure测量
        mPathMeasure.setPath(mPath, false)
        perCycleLength = mPathMeasure.length / (cycleCount + 2) //获取每一个周期路径的长度
        //将路径闭合形成水波
        mPath.lineTo(w + perCycleWidth * 2, h.toFloat())
        mPath.lineTo(0f, h.toFloat())
        mPath.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPathMeasure.getPosTan(mPathOffset, pos, tan)
        canvas.save()
        //第一条水波
        canvas.translate(-pos[0], 0f)
        canvas.drawPath(mPath, mPaint)
        canvas.save()
        //第二条水波（快）
        canvas.translate(-pos[0] - 2f, 0f)
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

        //再次测量加上当前的偏移（小船的位置）还与当前的进度值有关
        val boatOffset =
            mCurrentProgress / mMaxProgress * perCycleLength * cycleCount + mPathOffset //小船的位置
        mPathMeasure.getPosTan(boatOffset, pos, tan)

        canvas.translate((-mBoat.width / 2).toFloat(), -mBoat.height.toFloat()) //位置
        val degree = (atan2(tan[1], tan[0]) * 180 / Math.PI).toFloat()
        canvas.rotate(degree, pos[0] + mBoat.width / 2, pos[1] + mBoat.height)
        canvas.drawText(
            (mCurrentProgress / mMaxProgress * 100).toInt().toString() + "%",
            pos[0] + (if (mCurrentProgress > mMaxProgress / 2) -mBoat.width / 2 else mBoat.width / 2),
            pos[1], mPaint
        )

        canvas.drawBitmap(mBoat, pos[0], pos[1], mPaint)
        canvas.restore()
        mPathOffset += 1f
        if (mPathOffset >= perCycleLength) {
            mPathOffset = 0f
        }
        invalidate()
    }
}