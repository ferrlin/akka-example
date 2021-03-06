package remote.jv;

import remote.sc.*;
import akka.actor.UntypedActor;

public class WordCounterWorker extends UntypedActor {
	@Override
	public void onReceive(Object message) {
		if(message instanceof FileToCount){
			FileToCount c = (FileToCount)message;
			Integer count = c.countWords();
			getSender().tell(new WordCount(c.url(), count),
				getSelf());
		}else {
			throw new IllegalArgumentException("Unknown message" + message);	
		}
	}
}