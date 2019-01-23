package git.lxy.com.wananzhuoapp.ui.web

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.widget.FrameLayout
import com.article.demos.common.base.BaseActivity
import com.just.agentweb.AgentWeb
import git.lxy.com.wananzhuoapp.R
import kotlinx.android.synthetic.main.wananzhuo_activity_web.*

/**
 *  @author lxy
 *  @date 2018/9/4
 *  @Description
 */
class WebActivity : BaseActivity() {

    private lateinit var webAgent: AgentWeb
    private lateinit var url: String

    companion object {

        val ARTICLE_URL: String = "article_url"

        fun openActivity(context: Context?, url: String) {

            Intent(context, WebActivity::class.java).run {
                putExtra(ARTICLE_URL, url)
                context?.startActivity(this)
            }
        }
    }

    override fun getLayoutId() = R.layout.wananzhuo_activity_web

    override fun init() {
        intent?.let {
            url = it.getStringExtra(ARTICLE_URL)
        }

        webAgent = AgentWeb.with(this)
                .setAgentWebParent(webLayout, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }

    override fun onResume() {
        webAgent.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        webAgent.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        webAgent.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (webAgent.handleKeyEvent(keyCode, event)) {
            return true
        } else {
            finish()
            return super.onKeyDown(keyCode, event)
        }
    }
}