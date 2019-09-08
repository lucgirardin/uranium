package pl.karol202.uranium.swing.frame

import pl.karol202.uranium.swing.SwingBuilder
import pl.karol202.uranium.swing.SwingElement
import java.awt.Dimension
import javax.swing.JFrame

class SwingFrameBuilder internal constructor(private val rootSupplier: SwingBuilder.() -> SwingElement<*>)
{
	private var defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	private var isVisible = true
	private var pack = true
	private var size: Dimension? = null
	private var title: String? = null

	fun withDefaultCloseOperation(defaultCloseOperation: Int) = also { this.defaultCloseOperation = defaultCloseOperation }

	fun withVisibility(visibility: Boolean) = also { this.isVisible = visibility }

	fun withSize(width: Int, height: Int) = also {
		this.size = Dimension(width, height)
		this.pack = false
	}

	fun withTitle(title: String) = also { this.title = title }

	fun build() = object : SwingFrame()
	{
		override val SwingBuilder.rootElement get() = rootSupplier()

		override fun JFrame.initFrame()
		{
			val builder = this@SwingFrameBuilder

			defaultCloseOperation = builder.defaultCloseOperation
			isVisible = builder.isVisible
			builder.size?.let { size = it }
			builder.title?.let { title = it }
			if(builder.pack) pack()
		}
	}

	fun show() = build().show()
}