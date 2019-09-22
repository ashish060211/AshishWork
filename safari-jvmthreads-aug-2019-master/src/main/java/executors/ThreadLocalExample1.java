package executors;

import java.util.UUID;

public class ThreadLocalExample1 {
	
	ThreadLocal<Example> tl = null;
	ThreadLocalExample1 () {
		tl = new ThreadLocal<Example>();
	}

	public static void main(String[] args) {
		ThreadLocalExample1 t1 = new ThreadLocalExample1();
		ThreadLocalExample1 t2 = new ThreadLocalExample1();
		ThreadLocalExample1 t3 = new ThreadLocalExample1();

		t1.beforCommit();
		t2.beforCommit();
		t2.afterCommit();
		t3.beforCommit();
		t3.afterCommit();
		t1.afterCommit();
	}
	
	void beforCommit() {
		Example ex = new Example(UUID.randomUUID().toString());
		tl.set(ex);
		System.out.println("before commit end..."+ex);
	}
	void afterCommit() {
		System.out.println("after commit end..."+tl.get());
	}
	
	class Example {
		String name;
		public Example(String name) {
			super();
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "Example [name=" + name + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Example other = (Example) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		private ThreadLocalExample1 getEnclosingInstance() {
			return ThreadLocalExample1.this;
		}
		
	}

}
