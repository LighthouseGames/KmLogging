import co.touchlab.stately.concurrency.AtomicReference
import org.lighthousegames.logging.LogFactory
import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
internal val logFactory: AtomicReference<LogFactory?> = AtomicReference(null)
