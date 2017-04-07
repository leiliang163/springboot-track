package com.mjoys.springboot.track.biz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * The Class TimeProfileTool.
 */
public class TimeProfileUtils
{
	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(TimeProfileUtils.class);
	
	/** The stack. */
	private static ThreadLocal<Stack<TimeProfileStack>> stack = new ThreadLocal<>();
	
	/** The logs. */
	private static ThreadLocal<List<TimeProfileStack>> logs = new ThreadLocal<>();
	
	/** The request. */
	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
	
	/** The threshold. */
	private static int threshold = 2000;// 超时时间，单位毫秒
	
	/**
	 * The Constructor.
	 */
	private TimeProfileUtils(){
	    
	}
	
    /**
     * Gets the threshold.
     *
     * @return the threshold
     */
    public static int getThreshold() {
        return threshold;
    }

    
    /**
     * Sets the threshold.
     *
     * @param threshold the threshold
     */
    public static void setThreshold(int threshold) {
        TimeProfileUtils.threshold = threshold;
    }

    
    /**
     * Gets the request.
     *
     * @return the request
     */
    public static ThreadLocal<HttpServletRequest> getRequest() {
        return request;
    }


    
    /**
     * Sets the request.
     *
     * @param request the request
     */
    public static void setRequest(ThreadLocal<HttpServletRequest> request) {
        TimeProfileUtils.request = request;
    }


    /**
     * Enter.
     */
    public static void enter()
	{
		enter("");
	}
	
	/**
	 * Enter.
	 *
	 * @param tag the tag
	 */
	public static void enter(String tag)
	{
		if (stack.get() == null)
		{
			return;
		}
		TimeProfileStack t = new TimeProfileStack();
		t.setEnterTime(new Date());
		t.setTag(tag);
		t.setDeep(stack.get().size());
		
		t.index = logs.get().size() + stack.get().size();
		stack.get().push(t);
	}
	
	/**
	 * Release.
	 */
	public static void release()
	{
		if (stack.get() == null)
		{
			return;
		}
		TimeProfileStack t = stack.get().pop();
		t.setReleaseTime(new Date());
		logs.get().add(t);
	}
	
	/**
	 * Start.
	 */
	public static void start()
	{
		stack.set(new Stack<TimeProfileStack>());
		logs.set(new ArrayList<TimeProfileStack>());
		enter();
	}
	
	/**
	 * End.
	 */
	public static void end()
	{
		TimeProfileStack t = stack.get() == null ? null :stack.get().pop();
		if(t == null) {
			return;
		}
		t.setReleaseTime(new Date());
		logs.get().add(t);
		
		Long timeconsume = t.getReleaseTime().getTime() - t.getEnterTime().getTime();
		if (timeconsume > threshold)
		{
			// 输出日志
			StringBuilder sb = new StringBuilder();
			if (request.get() != null)
			{
				String url = RequestUtils.getRequestUrl(request.get());
				sb.append("TimeProfile timeout").append(timeconsume).append("ms >").append(threshold)
						.append("ms, url=").append(url);
			}
			else
			{
				sb.append("TimeProfile timeout ").append(timeconsume).append("ms ");
			}
			List<TimeProfileStack> list = new ArrayList<>(logs.get());
			Collections.sort(list, new Comparator<TimeProfileStack>()
			{
				
				@Override
				public int compare(TimeProfileStack o1, TimeProfileStack o2)
				{
					if (o1.index > o2.index)
					{
						return 1;
					}
					else if (o1.index < o2.index)
					{
						return -1;
					}
					return 0;
				}
			});
			for (TimeProfileStack s : list)
			{
				sb.append("\r\n\t");
				for (int i = 0; i < s.getDeep(); i++)
				{
					sb.append("-");
				}
				Long consume = s.getReleaseTime().getTime() - s.getEnterTime().getTime();
				sb.append(consume * 100 / timeconsume).append("% ").append(consume).append("ms");
				if (s.getTag() != null)
				{
					sb.append("  ").append(s.getTag());
				}
				
				if (s.getStackTraceElement() != null)
				{
					StackTraceElement ste = s.getStackTraceElement();
					sb.append("  ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append(" line:")
							.append(ste.getLineNumber());
				}
			}
			log.info(sb.toString());
		}
		request.set(null);
		stack.set(null);
		logs.set(null);
	}
	
	/**
	 * dump堆栈信息.
	 *
	 * @param ms 毫秒 ，超过此毫秒时才会dump
	 * @return the string
	 */
	public static String dump(long ms)
	{
		if (logs.get() == null)
		{
			return null;
		}
		TimeProfileStack t = stack.get().pop();
		t.setReleaseTime(new Date());
		logs.get().add(t);
		
		Long timeconsume = 0L;
		for (TimeProfileStack s : logs.get())
		{
			if (s.deep == 0)
			{
				timeconsume += s.getReleaseTime().getTime() - s.getEnterTime().getTime();
			}
		}
		if (timeconsume < ms)
		{
			return null;
		}
		// 输出日志
		StringBuilder sb = new StringBuilder();
		sb.append("TimeProfile timeout ").append(timeconsume).append("ms ");
		List<TimeProfileStack> list = new ArrayList<>(logs.get());
		Collections.sort(list, new Comparator<TimeProfileStack>()
		{
			
			@Override
			public int compare(TimeProfileStack o1, TimeProfileStack o2)
			{
				if (o1.index > o2.index)
				{
					return 1;
				}
				else if (o1.index < o2.index)
				{
					return -1;
				}
				return 0;
			}
		});
		for (TimeProfileStack s : list)
		{
			Long consume = s.getReleaseTime().getTime() - s.getEnterTime().getTime();
			if (consume == 0)
			{
				continue;
			}
			sb.append("\r\n\t");
			for (int i = 0; i < s.getDeep(); i++)
			{
				sb.append("  ");
			}
			sb.append("-").append(consume * 100 / timeconsume).append("%").append("  ").append(consume).append("ms");
			if (s.getTag() != null)
			{
				sb.append("  ").append(s.getTag());
			}
			
			if (s.getStackTraceElement() != null)
			{
				StackTraceElement ste = s.getStackTraceElement();
				sb.append("  ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append(" line:")
						.append(ste.getLineNumber());
			}
		}
		request.set(null);
		stack.set(null);
		logs.set(null);
		
		return sb.toString();
	}
	
	/**
	 * The Class TimeProfileStack.
	 */
	public static class TimeProfileStack
	{
		
		/** The enter time. */
		private Date enterTime;
		
		/** The release time. */
		private Date releaseTime;
		
		/** The deep. */
		private int deep;
		
		/** The tag. */
		private String tag;
		
		/** The index. */
		int index;
		
		/** The stack trace element. */
		private StackTraceElement stackTraceElement;
		
		/**
		 * Gets the enter time.
		 *
		 * @return the enter time
		 */
		public Date getEnterTime()
		{
			return enterTime;
		}
		
		/**
		 * Sets the enter time.
		 *
		 * @param enterTime the enter time
		 */
		public void setEnterTime(Date enterTime)
		{
			this.enterTime = enterTime;
		}
		
		/**
		 * Gets the release time.
		 *
		 * @return the release time
		 */
		public Date getReleaseTime()
		{
			return releaseTime;
		}
		
		/**
		 * Sets the release time.
		 *
		 * @param releaseTime the release time
		 */
		public void setReleaseTime(Date releaseTime)
		{
			this.releaseTime = releaseTime;
		}
		
		/**
		 * Gets the deep.
		 *
		 * @return the deep
		 */
		public int getDeep()
		{
			return deep;
		}
		
		/**
		 * Sets the deep.
		 *
		 * @param deep the deep
		 */
		public void setDeep(int deep)
		{
			this.deep = deep;
		}
		
		/**
		 * Gets the tag.
		 *
		 * @return the tag
		 */
		public String getTag()
		{
			return tag;
		}
		
		/**
		 * Sets the tag.
		 *
		 * @param tag the tag
		 */
		public void setTag(String tag)
		{
			this.tag = tag;
		}
		
		/**
		 * Gets the stack trace element.
		 *
		 * @return the stack trace element
		 */
		public StackTraceElement getStackTraceElement()
		{
			return stackTraceElement;
		}
		
		/**
		 * Sets the stack trace element.
		 *
		 * @param stackTraceElement the stack trace element
		 */
		public void setStackTraceElement(StackTraceElement stackTraceElement)
		{
			this.stackTraceElement = stackTraceElement;
		}
	}
}
